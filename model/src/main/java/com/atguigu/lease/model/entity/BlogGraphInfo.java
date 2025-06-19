package com.atguigu.lease.model.entity;

import com.atguigu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "图片信息表")
@TableName(value = "blog_graph_info")
@Data
public class BlogGraphInfo extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Schema(description = "图片名称")
    @TableField(value = "name")
    private String name;

    @Schema(description = "图片所属博客id")
    @TableField(value = "blog_id")
    private Long blogId;

    @Schema(description = "图片地址")
    @TableField(value = "url")
    private String url;
}
