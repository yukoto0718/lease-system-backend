package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.BlogFollow;
import com.atguigu.lease.web.app.vo.message.FollowUserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogFollowService extends IService<BlogFollow> {
    /**
     * 关注用户
     * @param followUserId 要关注的用户ID
     * @return 是否成功
     */
    boolean followUser(Long followUserId);

    /**
     * 取消关注用户
     * @param followUserId 要取消关注的用户ID
     * @return 是否成功
     */
    boolean unfollowUser(Long followUserId);

    /**
     * 查询是否已关注用户
     * @param followUserId 要查询的用户ID
     * @return 是否已关注
     */
    boolean isFollowing(Long followUserId);

    /**
     * 获取用户关注列表
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 关注列表
     */
    IPage<FollowUserVo> getFollowingList(Page<FollowUserVo> page, String keyword);

    /**
     * 获取用户粉丝列表
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 粉丝列表
     */
    IPage<FollowUserVo> getFollowerList(Page<FollowUserVo> page, String keyword);

    /**
     * 获取用户互相关注列表
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return 互相关注列表
     */
    IPage<FollowUserVo> getMutualFollowList(Page<FollowUserVo> page, String keyword);
}
