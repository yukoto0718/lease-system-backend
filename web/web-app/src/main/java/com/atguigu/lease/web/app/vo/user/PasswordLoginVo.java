package com.atguigu.lease.web.app.vo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "密码登录对象")
public class PasswordLoginVo {

    @Schema(description = "账号(邮箱或手机号)")
    private String account;

    @Schema(description = "密码")
    private String password;
}
