package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.email.EmailService;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.common.utils.VerifyCodeUtil;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import com.atguigu.lease.web.app.service.LoginService;
import com.atguigu.lease.web.app.service.SmsService;
import com.atguigu.lease.web.app.service.UserInfoService;
import com.atguigu.lease.web.app.vo.user.LoginVo;
import com.atguigu.lease.web.app.vo.user.PasswordLoginVo;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmailService emailService;

    @Autowired
    //private RedisTemplate redisTemplate;//为了避免客户端序列化异常，可以使用 StringRedisTemplate.
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper;

//    @Autowired
//    private UserInfoMapper userInfoMapper;

    @Override
    public void getSMSCode(String email, String language) {  // 保持方法签名不变，但参数现在传入邮箱
        //1. 检查邮箱是否为空
        if (!StringUtils.hasText(email)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_EMAIL_EMPTY);
        }

        //2. 检查Redis中是否已经存在该邮箱的key
        String key = RedisConstant.APP_LOGIN_PREFIX + email;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            //若存在，则检查其存在的时间
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                //若存在时间不足一分钟，响应发送过于频繁
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        //3.生成验证码并发送邮件，将验证码存入Redis
        String verifyCode = VerifyCodeUtil.getVerifyCode(6);
        // 如果没有传入language参数，使用默认值"zh"
        String effectiveLanguage = language != null ? language : "zh";

        // 传递三个参数给邮件服务
        emailService.sendVerificationCode(email, verifyCode, effectiveLanguage);
        redisTemplate.opsForValue().set(key, verifyCode, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断邮箱和验证码是否为空
        if (!StringUtils.hasText(loginVo.getEmail())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_EMAIL_EMPTY);
        }

        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }

        //2.校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getEmail();
        String code = redisTemplate.opsForValue().get(key);
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }

        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getEmail, loginVo.getEmail());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setEmail(loginVo.getEmail());
            userInfo.setStatus(BaseStatus.ENABLE);

            // 从邮箱生成昵称
            String nickname;
            if (loginVo.getEmail().contains("@")) {
                nickname = "用户-" + loginVo.getEmail().substring(0, loginVo.getEmail().indexOf('@'));
            } else {
                nickname = "用户-" + loginVo.getEmail().substring(6);
            }

            userInfo.setNickname(nickname);
            userInfoService.save(userInfo);
        }

        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }

        //5.创建并返回TOKEN
        return JwtUtil.createToken(userInfo.getId(), loginVo.getEmail());
    }

    @Override
    public UserInfoVo getLoginUserById(Long userId) {
//        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfo userInfo = userInfoService.getById(userId);
        return new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
    }

    @Override
    public String loginWithPassword(PasswordLoginVo passwordLoginVo) {
        String account = passwordLoginVo.getAccount();
        String password = passwordLoginVo.getPassword();

        // 1. 检查账号和密码是否为空
        if (!StringUtils.hasText(account)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_EMAIL_EMPTY);
        }

        if (!StringUtils.hasText(password)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PASSWORD_EMPTY);
        }

        // 2. 使用自定义Mapper方法查询用户（获取到密码）
        UserInfo userInfo = userInfoMapper.selectOneByAccount(account);

        // 3. 检查用户是否存在
        if (userInfo == null) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_NOT_EXIST_ERROR);
        }

        // 添加调试信息
//        String inputPasswordMd5 = DigestUtils.md5Hex(password);
//        System.out.println("输入的密码: " + password);
//        System.out.println("输入密码MD5值: " + inputPasswordMd5);
//        System.out.println("数据库密码: " + userInfo.getPassword());

        // 4. 检查用户是否被禁用
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }

        // 用户存在性检查后，添加密码检查
        if (userInfo.getPassword() == null) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_NOT_SET_PASSWORD);
        }

        // 5. 验证密码 - 使用MD5加密方式
        if (!userInfo.getPassword().equals(DigestUtils.md5Hex(password))) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_ERROR);
        }

        // 6. 创建JWT令牌
        boolean isEmail = account.contains("@");
        String emailOrPhone = isEmail ? account : userInfo.getEmail();
        return JwtUtil.createToken(userInfo.getId(), emailOrPhone);
    }

}
