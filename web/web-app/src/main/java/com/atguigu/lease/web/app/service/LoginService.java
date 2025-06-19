package com.atguigu.lease.web.app.service;

import com.atguigu.lease.web.app.vo.user.LoginVo;
import com.atguigu.lease.web.app.vo.user.PasswordLoginVo;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void getSMSCode(String email,String language);

    String login(LoginVo loginVo);

    UserInfoVo getLoginUserById(Long userId);

    String loginWithPassword(PasswordLoginVo passwordLoginVo);
}
