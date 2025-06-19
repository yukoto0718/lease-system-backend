package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.app.vo.user.UserSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 更新用户基本信息
     * @param userId 用户ID
     * @param userSubmitVo 用户提交的信息
     */
    void updateUserBaseInfo(Long userId, UserSubmitVo userSubmitVo);
}