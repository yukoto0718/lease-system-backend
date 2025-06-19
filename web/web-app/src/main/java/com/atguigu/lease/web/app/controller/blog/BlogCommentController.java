package com.atguigu.lease.web.app.controller.blog;

import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.IBlogCommentService;
import com.atguigu.lease.web.app.vo.blog.CommentSubmitVo;
import com.atguigu.lease.web.app.vo.blog.CommentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "博客评论")
@RequestMapping("/app/blog/comment")
@RestController
public class BlogCommentController {

    @Resource
    private IBlogCommentService blogCommentService;

    @Operation(summary = "获取博客评论列表")
    @GetMapping("list/{blogId}")
    public Result<List<CommentVo>> getCommentList(@PathVariable("blogId") Long blogId) {
        List<CommentVo> commentList = blogCommentService.getCommentListByBlogId(blogId);
        return Result.ok(commentList);
    }

    @Operation(summary = "提交评论")
    @PostMapping("submit")
    public Result submitComment(@RequestBody CommentSubmitVo commentSubmitVo) {
        blogCommentService.saveComment(commentSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("delete/{commentId}")
    public Result deleteComment(@PathVariable("commentId") Long commentId) {
        blogCommentService.removeComment(commentId);
        return Result.ok();
    }
}