package com.atguigu.lease.web.app.vo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "APP端登录实体")
public class LoginVo {

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "短信验证码")
    private String code;
}
