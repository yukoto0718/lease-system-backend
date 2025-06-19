package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.model.entity.BlogGraphInfo;
import com.atguigu.lease.model.entity.BlogInfo;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BusinessType;
import com.atguigu.lease.web.app.mapper.BlogInfoMapper;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import com.atguigu.lease.web.app.service.BlogFollowService;
import com.atguigu.lease.web.app.service.BlogGraphService;
import com.atguigu.lease.web.app.service.FileManagementService;
import com.atguigu.lease.web.app.service.IBlogInfoService;
import com.atguigu.lease.web.app.vo.blog.BlogDetailVo;
import com.atguigu.lease.web.app.vo.blog.BlogGraphVo;
import com.atguigu.lease.web.app.vo.blog.BlogListVo;
import com.atguigu.lease.web.app.vo.blog.BlogSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements IBlogInfoService {

    @Autowired
    private BlogGraphService blogGraphService;
    // 显式注入Mapper，与项目风格保持一致
    @Autowired
    private BlogInfoMapper blogInfoMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BlogFollowService blogFollowService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private FileManagementService fileManagementService; // 注入文件管理服务

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateBlog(BlogSubmitVo blogSubmitVo) {
        // 从LoginUserHolder获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 设置用户ID到VO对象
        blogSubmitVo.setUserId(userId);

        // 1. 判断是新增还是更新
        boolean isUpdate = blogSubmitVo.getId() != null;

        // 收集旧图片URL（当更新博客时）
        List<String> oldImageUrls = new ArrayList<>();
        if (isUpdate) {
            // 查询博客当前的图片
            LambdaQueryWrapper<BlogGraphInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogGraphInfo::getBlogId, blogSubmitVo.getId());
            List<BlogGraphInfo> oldGraphs = blogGraphService.list(queryWrapper);

            // 收集旧的图片URL
            oldImageUrls = oldGraphs.stream()
                    .map(BlogGraphInfo::getUrl)
                    .collect(Collectors.toList());

            // 删除原有的图片关联
            blogGraphService.remove(queryWrapper);
        }

        // 2. 保存博客基本信息
        super.saveOrUpdate(blogSubmitVo);

        // 3. 插入新的图片列表
        List<BlogGraphVo> graphVoList = blogSubmitVo.getBlogGraphVoList();
        List<String> newImageUrls = new ArrayList<>(); // 收集新图片URL

        if (!CollectionUtils.isEmpty(graphVoList)) {
            ArrayList<BlogGraphInfo> blogGraphInfoList = new ArrayList<>();
            for (BlogGraphVo blogGraphVo : graphVoList) {
                BlogGraphInfo graphInfo = new BlogGraphInfo();
                graphInfo.setBlogId(blogSubmitVo.getId());
                graphInfo.setName(blogGraphVo.getName());
                graphInfo.setUrl(blogGraphVo.getUrl());
                blogGraphInfoList.add(graphInfo);

                // 收集新图片URL
                newImageUrls.add(blogGraphVo.getUrl());
            }

            // 确认图片状态（将临时图片标记为已确认使用）
            Map<String, String> urlMappings = fileManagementService.confirmFiles(newImageUrls, BusinessType.BLOG, blogSubmitVo.getId());

            // 更新blogGraphInfo中的URL，确保与file_management中的URL一致
            if (!urlMappings.isEmpty()) {
                for (BlogGraphInfo graphInfo : blogGraphInfoList) {
                    String newUrl = urlMappings.get(graphInfo.getUrl());
                    if (newUrl != null) {
                        graphInfo.setUrl(newUrl);
                    }
                }
            }

            blogGraphService.saveBatch(blogGraphInfoList);
        }

        // 4. 处理不再使用的旧图片
        if (isUpdate && !CollectionUtils.isEmpty(oldImageUrls)) {
            // 找出旧图片中不在新图片列表的图片URL
            List<String> imagesToProcess = oldImageUrls.stream()
                    .filter(url -> !newImageUrls.contains(url))
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(imagesToProcess)) {
                // 将不再使用的图片移回temp目录，而不是直接删除
                Map<String, String> movedUrlMappings = fileManagementService.moveFilesToTemp(imagesToProcess);
            }
        }
    }

    @Override
    public BlogDetailVo getBlogDetailById(Long id) {
        // 一次性查询博客和图片信息，避免N+1查询
        BlogDetailVo blogDetailVo = blogInfoMapper.selectBlogDetailWithImages(id);
        if (blogDetailVo == null) {
            return null;
        }

        // 确保imageList不为null
        if (blogDetailVo.getImageList() == null) {
            blogDetailVo.setImageList(new ArrayList<>());
        }

        try {
            // 获取当前登录用户ID
            Long currentUserId = LoginUserHolder.getLoginUser().getUserId();

            if (currentUserId != null) {
                // 查询当前用户的点赞状态
                String key = RedisConstant.APP_BLOG_LIKED_KEY + id;
                Double score = stringRedisTemplate.opsForZSet().score(key, currentUserId.toString());
                blogDetailVo.setIsLiked(score != null);

                // 判断是否是自己的博客
                boolean isOwnBlog = currentUserId.equals(blogDetailVo.getUserId());
                blogDetailVo.setIsOwnBlog(isOwnBlog);

                // 只有不是自己的博客时，才需要查询是否已关注
                if (!isOwnBlog) {
                    // 查询是否已关注博主
                    // 注意需要注入 BlogFollowService
                    boolean isFollowed = blogFollowService.isFollowing(blogDetailVo.getUserId());
                    blogDetailVo.setIsFollowed(isFollowed);
                } else {
                    // 自己的博客，设置为未关注
                    blogDetailVo.setIsFollowed(false);
                }
            } else {
                // 用户未登录，设置默认值
                blogDetailVo.setIsLiked(false);
                blogDetailVo.setIsFollowed(false);
                blogDetailVo.setIsOwnBlog(false);
            }
        } catch (Exception e) {
            // 处理异常情况
            blogDetailVo.setIsLiked(false);
            blogDetailVo.setIsFollowed(false);
            blogDetailVo.setIsOwnBlog(false);
        }

        return blogDetailVo;
    }

    @Override
    public IPage<BlogListVo> getBlogList(Page<BlogListVo> page) {
        //使用baseMapper：代码更简洁，不需要额外注入Mapper，但对新手可能不太直观
        //直接注入Mapper：代码更直观，特别是对新手，但需要额外的注入代码
        //IPage<BlogListVo> blogListVoPage = baseMapper.selectBlogList(page);
        IPage<BlogListVo> blogListVoPage = blogInfoMapper.selectBlogList(page);

        // 处理可能的空值，设置默认值
        for (BlogListVo blogListVo : blogListVoPage.getRecords()) {
            if (blogListVo.getNickname() == null) {
                blogListVo.setNickname("User" + blogListVo.getUserId());
            }
            if (blogListVo.getAvatar() == null) {
                blogListVo.setAvatar("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            }
            // 处理空的图片列表
            if (blogListVo.getImageList() == null) {
                blogListVo.setImageList(new ArrayList<>());
            }
            // 查询用户点赞状态
            try {
                // 获取当前登录用户ID
                Long currentUserId = LoginUserHolder.getLoginUser().getUserId();
                // 设置是否点赞
                String key = RedisConstant.APP_BLOG_LIKED_KEY + blogListVo.getId();
                Double score = stringRedisTemplate.opsForZSet().score(key, currentUserId.toString());
                blogListVo.setIsLiked(score != null);
            } catch (Exception e) {
                blogListVo.setIsLiked(false);
            }
        }

        return blogListVoPage;
    }

    @Override
    public void likeBlog(Long id) {

        //1.从LoginUserHolder获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        //2.判断当前用户是否已经点赞
        String key = RedisConstant.APP_BLOG_LIKED_KEY + id;
        Double score = stringRedisTemplate.opsForZSet().score(key,userId.toString());
        boolean isliked = score != null;
        //3.使用Lambda表达式和链式调用更新数据库
        LambdaUpdateWrapper<BlogInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BlogInfo::getId,id)
                     .setSql(isliked ? "liked = liked - 1":"liked = liked + 1");

        boolean isSuccess = this.update(updateWrapper);

        //4.更新Redis
        if (isSuccess){
            if (isliked){
                //取消点赞，从Redis移除
                stringRedisTemplate.opsForZSet().remove(key,userId.toString());
            }else{
                //点赞，添加到Redis
                stringRedisTemplate.opsForZSet().add(key,userId.toString(),System.currentTimeMillis());
            }
        }
    }

    @Override
    public IPage<BlogListVo> getUserBlogList(Page<BlogListVo> page, Long userId) {
        // 调用Mapper查询特定用户的博客列表
        IPage<BlogListVo> blogListVoPage = blogInfoMapper.selectUserBlogList(page, userId);

        // 处理可能的空值，设置默认值
        for (BlogListVo blogListVo : blogListVoPage.getRecords()) {
            if (blogListVo.getNickname() == null) {
                blogListVo.setNickname("User" + blogListVo.getUserId());
            }
            if (blogListVo.getAvatar() == null) {
                blogListVo.setAvatar("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            }
            // 处理空的图片列表
            if (blogListVo.getImageList() == null) {
                blogListVo.setImageList(new ArrayList<>());
            }

            try {
                // 获取当前登录用户ID
                Long currentUserId = LoginUserHolder.getLoginUser().getUserId();
                // 设置是否点赞
                String key = RedisConstant.APP_BLOG_LIKED_KEY + blogListVo.getId();
                Double score = stringRedisTemplate.opsForZSet().score(key, currentUserId.toString());
                blogListVo.setIsLiked(score != null);
            } catch (Exception e) {
                blogListVo.setIsLiked(false);
            }
        }

        return blogListVoPage;
    }

    @Override
    public Map<String, Object> getUserSpaceInfo(Long userId) {
        Map<String, Object> result = new HashMap<>();

        // 查询用户信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        if (userInfo != null) {
            result.put("userId", userInfo.getId());
            result.put("nickname", userInfo.getNickname());
        } else {
            result.put("userId", userId);
            result.put("nickname", "User" + userId);
        }

        // 检查当前用户与目标用户的关系
        try {
            Long currentUserId = LoginUserHolder.getLoginUser().getUserId();
            result.put("isOwnSpace", currentUserId.equals(userId));

            if (!currentUserId.equals(userId)) {
                boolean isFollowing = blogFollowService.isFollowing(userId);
                result.put("isFollowing", isFollowing);
            } else {
                result.put("isFollowing", false);
            }
        } catch (Exception e) {
            result.put("isOwnSpace", false);
            result.put("isFollowing", false);
        }

        return result;
    }
}
