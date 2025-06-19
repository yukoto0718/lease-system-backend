package com.atguigu.lease.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    FAIL(201, "fail"),
    PARAM_ERROR(202, "param_error"),
    SERVICE_ERROR(203, "service_error"),
    DATA_ERROR(204, "data_error"),
    ILLEGAL_REQUEST(205, "illegal_request"),
    REPEAT_SUBMIT(206, "repeat_submit"),
    DELETE_ERROR(207, "delete_error"),

    ADMIN_ACCOUNT_EXIST_ERROR(301, "admin_account_exist_error"),
    ADMIN_CAPTCHA_CODE_ERROR(302, "admin_captcha_code_error"),
    ADMIN_CAPTCHA_CODE_EXPIRED(303, "admin_captcha_code_expired"),
    ADMIN_CAPTCHA_CODE_NOT_FOUND(304, "admin_captcha_code_not_found"),


    ADMIN_LOGIN_AUTH(305, "admin_login_auth"),
    ADMIN_ACCOUNT_NOT_EXIST_ERROR(306, "admin_account_not_exist_error"),
    ADMIN_ACCOUNT_ERROR(307, "admin_account_error"),
    ADMIN_ACCOUNT_DISABLED_ERROR(308, "admin_account_disabled_error"),
    ADMIN_ACCESS_FORBIDDEN(309, "admin_access_forbidden"),

    ADMIN_APARTMENT_DELETE_ERROR(310, "admin_apartment_delete_error"),

    APP_LOGIN_AUTH(501, "app_login_auth"),
    APP_LOGIN_PHONE_EMPTY(502, "app_login_phone_empty"),
    APP_LOGIN_CODE_EMPTY(503, "app_login_code_empty"),
    APP_SEND_SMS_TOO_OFTEN(504, "app_send_sms_too_often"),
    APP_LOGIN_CODE_EXPIRED(505, "app_login_code_expired"),
    APP_LOGIN_CODE_ERROR(506, "app_login_code_error"),
    APP_ACCOUNT_DISABLED_ERROR(507, "app_account_disabled_error"),

    APP_LOGIN_EMAIL_EMPTY(2001, "app_login_email_empty"),
    APP_LOGIN_EMAIL_INVALID(2002, "app_login_email_invalid"),
    APP_SEND_EMAIL_TOO_OFTEN(2003, "app_send_email_too_often"),

    APP_LOGIN_PASSWORD_EMPTY(2005, "app_login_password_empty"),
    APP_ACCOUNT_NOT_EXIST_ERROR(2006, "app_account_not_exist_error"),
    APP_ACCOUNT_ERROR(2007, "app_account_error"),
    APP_ACCOUNT_NOT_SET_PASSWORD(2008, "app_account_not_set_password"),


    TOKEN_EXPIRED(601, "token_expired"),
    TOKEN_INVALID(602, "token_invalid");


    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
