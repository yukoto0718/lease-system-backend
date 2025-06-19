package com.atguigu.lease.common.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    //原本传入的参数是String email, String verifyCode
    @Override
    public void sendVerificationCode(String email, String verifyCode,String language) {

        // 根据语言选择邮件内容
        String subject, content;
        switch(language) {
            case "en":
                subject = "U+ Apartment Verification Code";
                content = "Your verification code is: " + verifyCode + ", valid for 5 minutes. Please do not share with others.";
                break;
            case "ja":
                subject = "U+アパートメント 認証コード";
                content = "認証コードは: " + verifyCode + "、有効期限は5分です。他人に漏らさないでください。";
                break;
            default: // 中文
                subject = "U+公寓 登录验证码";
                content = "您的验证码是: " + verifyCode + "，有效期为5分钟，请勿泄露给他人。";
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
//        message.setSubject("登录验证码");
//        message.setText("您的验证码是: " + verifyCode + "，有效期为5分钟，请勿泄露给他人。");
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
