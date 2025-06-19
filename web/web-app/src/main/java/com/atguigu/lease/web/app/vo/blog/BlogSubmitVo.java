package com.atguigu.lease.web.app.vo.blog;

import com.atguigu.lease.model.entity.BlogInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "发布博客信息")
public class BlogSubmitVo extends BlogInfo {

    @Schema(description = "博客ID（更新时使用）")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "博客文字描述")
    private String content;

    @Schema(description = "图片列表")
    private List<BlogGraphVo> blogGraphVoList;

    //可能要补充
}
