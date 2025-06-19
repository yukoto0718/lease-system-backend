package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.BlogFollow;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.app.mapper.BlogFollowMapper;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import com.atguigu.lease.web.app.service.BlogFollowService;
import com.atguigu.lease.web.app.vo.message.FollowUserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogFollowServiceImpl extends ServiceImpl<BlogFollowMapper, BlogFollow> implements BlogFollowService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BlogFollowMapper blogFollowMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean followUser(Long followUserId) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 不能关注自己
        if (userId.equals(followUserId)) {
            throw new LeaseException(ResultCodeEnum.FAIL);
        }

        // 检查要关注的用户是否存在
        UserInfo followUser = userInfoMapper.selectById(followUserId);
        if (followUser == null) {
            throw new LeaseException(ResultCodeEnum.DATA_ERROR);
        }

        // 检查是否已经关注
        Integer count = blogFollowMapper.checkFollowRelation(userId, followUserId);
        if (count > 0) {
            return true; // 已经关注过，直接返回成功
        }

        // 创建关注关系
        BlogFollow blogFollow = new BlogFollow();
        blogFollow.setUserId(userId);
        blogFollow.setFollowUserId(followUserId);

        return this.save(blogFollow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unfollowUser(Long followUserId) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 不能取消关注自己
        if (userId.equals(followUserId)) {
            throw new LeaseException(ResultCodeEnum.FAIL);
        }

        // 直接执行删除 SQL，不使用 MyBatis-Plus 的 remove 方法，以避开逻辑删除
        // 使用原生SQL删除记录
        return blogFollowMapper.deleteByUserIdAndFollowUserId(userId, followUserId) > 0;
    }

    @Override
    public boolean isFollowing(Long followUserId) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        // 查询是否存在关注关系
        Integer count = blogFollowMapper.checkFollowRelation(userId, followUserId);

        return count > 0;
    }

    @Override
    public IPage<FollowUserVo> getFollowingList(Page<FollowUserVo> page, String keyword) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        return blogFollowMapper.selectFollowingList(page, userId, keyword);
    }

    @Override
    public IPage<FollowUserVo> getFollowerList(Page<FollowUserVo> page, String keyword) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        return blogFollowMapper.selectFollowerList(page, userId, keyword);
    }

    @Override
    public IPage<FollowUserVo> getMutualFollowList(Page<FollowUserVo> page, String keyword) {
        // 获取当前登录用户ID
        Long userId = LoginUserHolder.getLoginUser().getUserId();

        return blogFollowMapper.selectMutualFollowList(page, userId, keyword);
    }
}
