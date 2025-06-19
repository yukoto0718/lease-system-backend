package com.atguigu.lease.web.app.controller.blog;

import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.IBlogInfoService;
import com.atguigu.lease.web.app.vo.blog.BlogDetailVo;
import com.atguigu.lease.web.app.vo.blog.BlogListVo;
import com.atguigu.lease.web.app.vo.blog.BlogSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "博客管理")
@RequestMapping("/app/blog")
@RestController
public class BlogController {

    @Resource
    private IBlogInfoService blogService;

    @Operation(summary = "保存博客信息")
    @PostMapping("saveBlog")
    public Result saveOrUpdateBlog(@RequestBody BlogSubmitVo blogSubmitVo){
        blogService.saveOrUpdateBlog(blogSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据id查询博客信息")
    @PostMapping("getBlogById")
    public Result<BlogDetailVo> getDetailById(@RequestParam Long id){
        BlogDetailVo blogDetailVo = blogService.getBlogDetailById(id);
        return Result.ok(blogDetailVo);
    }

    @Operation(summary = "获取博客列表")
    @GetMapping("listBlogs")
    public Result<IPage<BlogListVo>> listBlogs(@RequestParam long current, @RequestParam long size){
        Page<BlogListVo> page = new Page<>(current,size);
        IPage<BlogListVo> result = blogService.getBlogList(page);
        return Result.ok(result);
    }

    @Operation(summary = "修改点赞数量")
    @PostMapping("like/{id}")
    public Result likeBlog(@PathVariable("id") Long id){
        blogService.likeBlog(id);
        return Result.ok();
    }

    @Operation(summary = "获取特定用户的博客列表")
    @GetMapping("listUserBlogs/{userId}")
    public Result<IPage<BlogListVo>> listUserBlogs(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Page<BlogListVo> page = new Page<>(current, size);
        IPage<BlogListVo> result = blogService.getUserBlogList(page, userId);
        return Result.ok(result);
    }

    @Operation(summary = "获取用户博客空间信息")
    @GetMapping("getUserSpace/{userId}")
    public Result<Map<String, Object>> getUserSpace(@PathVariable("userId") Long userId) {
        Map<String, Object> result = blogService.getUserSpaceInfo(userId);
        return Result.ok(result);
    }

}
