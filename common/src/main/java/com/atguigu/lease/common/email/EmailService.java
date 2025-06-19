package com.atguigu.lease.common.email;

public interface EmailService {

    void sendVerificationCode(String email, String verifyCode, String language);

}
