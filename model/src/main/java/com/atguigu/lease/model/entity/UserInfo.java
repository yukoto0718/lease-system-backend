package com.atguigu.lease.model.entity;

import com.atguigu.lease.model.enums.BaseStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户信息表")
@TableName(value = "user_info")
@Data
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "手机号码")
    @TableField(value = "phone")
    private String phone;

    @Schema(description = "邮箱（用做登录用户名）")
    @TableField(value = "email")
    private String email;

    @Schema(description = "密码")
    @TableField(value = "password",select = false)//加了false查询的时候就不会显示出来了
    private String password;

    @Schema(description = "头像url")
    @TableField(value = "avatar_url")
    private String avatarUrl;

    @Schema(description = "昵称")
    @TableField(value = "nickname")
    private String nickname;

    @Schema(description = "账号状态")
    @TableField(value = "status")
    private BaseStatus status;


}