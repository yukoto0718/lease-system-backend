package com.atguigu.lease.web.app.controller.login;


import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.LoginService;
import com.atguigu.lease.web.app.vo.user.LoginVo;
import com.atguigu.lease.web.app.vo.user.PasswordLoginVo;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/app/")
public class LoginController {

    @Autowired
    private LoginService service;

    //    @GetMapping("login/getCode")
//    @Operation(summary = "获取短信验证码")
//    public Result getCode(@RequestParam String phone) {
//        return Result.ok();
//    }
    @GetMapping("login/getCode")
    @Operation(summary = "获取邮箱验证码")
    public Result getCode(@RequestParam String email,
                          @RequestParam(required = false) String language) {
        service.getSMSCode(email,language);
        return Result.ok();
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token = service.login(loginVo);
        return Result.ok(token);
    }

    //新增密码登录方法
    @PostMapping("login/password")
    @Operation(summary = "密码登录")
    public Result<String> loginWithPassword(@RequestBody PasswordLoginVo passwordLoginVo) {
        String token = service.loginWithPassword(passwordLoginVo);
        return Result.ok(token);
    }

    @GetMapping("info")
    @Operation(summary = "获取登录用户信息")
    public Result<UserInfoVo> info() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        UserInfoVo info = service.getLoginUserById(userId);
        return Result.ok(info);
    }
}