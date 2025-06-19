package com.atguigu.lease.model.entity;


import com.atguigu.lease.model.enums.BusinessType;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("file_management")
@Schema(description = "文件信息表")
public class FileManagement extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "原始文件名")
    @TableField(value = "original_filename")
    private String originalFilename;

    @Schema(description = "存储路径")
    @TableField(value = "path")
    private String path;

    @Schema(description = "访问URL")
    @TableField(value = "url")
    private String url;

    @Schema(description = "文件状态：0-临时，1-确认使用")
    @TableField(value = "status")
    private Integer status;

    @Schema(description = "业务类型")
    @TableField(value = "business_type")
    private BusinessType businessType;

    @Schema(description = "关联的业务ID")
    @TableField(value = "business_id")
    private Long businessId;

}