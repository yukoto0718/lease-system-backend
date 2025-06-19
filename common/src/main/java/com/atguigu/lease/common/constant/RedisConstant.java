package com.atguigu.lease.common.constant;

public class RedisConstant {
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;
    public static final String APP_LOGIN_PREFIX = "app:login:";
    public static final Integer APP_LOGIN_CODE_RESEND_TIME_SEC = 60;
    public static final Integer APP_LOGIN_CODE_TTL_SEC = 60 * 10;
    public static final String APP_ROOM_PREFIX = "app:room:";

    //添加自定义
    public static final String APP_BLOG_LIKED_KEY = "blog:liked:";

    // 用户未读消息总数的Redis键前缀，完整键为 app:message:unread:count:用户ID
    public static final String APP_MESSAGE_UNREAD_COUNT = "app:message:unread:count:";
}
