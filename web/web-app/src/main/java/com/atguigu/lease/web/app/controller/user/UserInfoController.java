package com.atguigu.lease.web.app.controller.user;

import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.app.service.UserInfoService;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;
import com.atguigu.lease.web.app.vo.user.UserSubmitVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RequestMapping("/app/user")
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/info")
    @Operation(summary = "更新用户基本信息")
    public Result<Void> updateUserInfo(@RequestBody UserSubmitVo userSubmitVo) {
       // 使用LoginUserHolder获取当前登录用户ID
       Long userId = LoginUserHolder.getLoginUser().getUserId();

       // 更新用户信息
       userInfoService.updateUserBaseInfo(userId, userSubmitVo);
       return Result.ok();
    }
}