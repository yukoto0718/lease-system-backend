package com.atguigu.lease.web.app.controller.blog;

import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.BlogFollowService;
import com.atguigu.lease.web.app.vo.message.FollowUserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户关注")
@RestController
@RequestMapping("/app/blog/follow")
public class BlogFollowController {

    @Autowired
    private BlogFollowService blogFollowService;

    @Operation(summary = "关注用户")
    @PostMapping("/follow/{followUserId}")
    public Result followUser(@PathVariable Long followUserId) {
        boolean result = blogFollowService.followUser(followUserId);
        return Result.ok(result);
    }

    @Operation(summary = "取消关注用户")
    @DeleteMapping("/unfollow/{followUserId}")
    public Result unfollowUser(@PathVariable Long followUserId) {
        boolean result = blogFollowService.unfollowUser(followUserId);
        return Result.ok(result);
    }

    @Operation(summary = "查询是否已关注用户")
    @GetMapping("/is-following/{followUserId}")
    public Result<Boolean> isFollowing(@PathVariable Long followUserId) {
        boolean isFollowing = blogFollowService.isFollowing(followUserId);
        return Result.ok(isFollowing);
    }

    //方法接收 Page 对象作为参数,返回 IPage 接口类型
    @Operation(summary = "获取关注列表")
    @GetMapping("/following")
    public Result<IPage<FollowUserVo>> getFollowingList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<FollowUserVo> page = new Page<>(current, size);
        IPage<FollowUserVo> result = blogFollowService.getFollowingList(page, keyword);
        return Result.ok(result);
    }

    @Operation(summary = "获取粉丝列表")
    @GetMapping("/followers")
    public Result<IPage<FollowUserVo>> getFollowerList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<FollowUserVo> page = new Page<>(current, size);
        IPage<FollowUserVo> result = blogFollowService.getFollowerList(page, keyword);
        return Result.ok(result);
    }

    @Operation(summary = "获取互相关注列表")
    @GetMapping("/mutual")
    public Result<IPage<FollowUserVo>> getMutualFollowList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<FollowUserVo> page = new Page<>(current, size);
        IPage<FollowUserVo> result = blogFollowService.getMutualFollowList(page, keyword);
        return Result.ok(result);
    }
}
