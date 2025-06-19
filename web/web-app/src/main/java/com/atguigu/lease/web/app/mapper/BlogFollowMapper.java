package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.BlogFollow;
import com.atguigu.lease.web.app.vo.message.FollowUserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface BlogFollowMapper extends BaseMapper<BlogFollow> {

    /**
     * 查询用户的关注用户列表
     * @param page 分页参数
     * @param userId 当前用户ID
     * @param keyword 搜索关键词
     * @return 关注用户列表
     */
    IPage<FollowUserVo> selectFollowingList(Page<FollowUserVo> page,
                                            @Param("userId") Long userId,
                                            @Param("keyword") String keyword);

    /**
     * 查询用户的粉丝列表
     * @param page 分页参数
     * @param userId 当前用户ID
     * @param keyword 搜索关键词
     * @return 粉丝列表
     */
    IPage<FollowUserVo> selectFollowerList(Page<FollowUserVo> page,
                                          @Param("userId") Long userId,
                                          @Param("keyword") String keyword);

    /**
     * 查询用户的互相关注列表
     * @param page 分页参数
     * @param userId 当前用户ID
     * @param keyword 搜索关键词
     * @return 互相关注列表
     */
    IPage<FollowUserVo> selectMutualFollowList(Page<FollowUserVo> page,
                                              @Param("userId") Long userId,
                                              @Param("keyword") String keyword);

    /**
     * 检查用户是否关注了指定用户
     * @param userId 当前用户ID
     * @param followUserId 关注的用户ID
     * @return 关注记录数量
     */
    Integer checkFollowRelation(@Param("userId") Long userId,
                                @Param("followUserId") Long followUserId);

    /**
     * 根据用户ID和关注用户ID删除关注记录
     * @param userId 用户ID
     * @param followUserId 关注的用户ID
     * @return 删除的记录数
     */
    @Delete("DELETE FROM blog_follow WHERE user_id = #{userId} AND follow_user_id = #{followUserId}")
    int deleteByUserIdAndFollowUserId(@Param("userId") Long userId, @Param("followUserId") Long followUserId);
}
