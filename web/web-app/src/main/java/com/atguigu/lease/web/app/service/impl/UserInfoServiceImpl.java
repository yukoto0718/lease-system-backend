package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.enums.BusinessType;
import com.atguigu.lease.web.app.service.FileManagementService;
import com.atguigu.lease.web.app.vo.user.UserSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.app.service.UserInfoService;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author liubo
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService{

    @Autowired
    private FileManagementService fileManagementService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserBaseInfo(Long userId, UserSubmitVo userSubmitVo) {
        // 验证昵称长度
        if (userSubmitVo.getNickname() != null && userSubmitVo.getNickname().length() > 20) {
            throw new IllegalArgumentException("昵称长度不能超过20个字符");
        }

        // 获取用户原有信息，用于检查头像是否变更
        UserInfo oldUserInfo = this.getById(userId);
        String oldAvatarUrl = (oldUserInfo != null) ? oldUserInfo.getAvatarUrl() : null;

        // 创建需要更新的对象
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setNickname(userSubmitVo.getNickname());
        userInfo.setAvatarUrl(userSubmitVo.getAvatarUrl());

        // 执行更新
        this.updateById(userInfo);

        // 处理头像文件
        String newAvatarUrl = userSubmitVo.getAvatarUrl();

        // 如果新头像URL不为空，且与旧头像不同，则确认新头像并可能处理旧头像
        if (StringUtils.hasText(newAvatarUrl)) {
            // 确认新头像文件
            List<String> avatarUrls = new ArrayList<>(Collections.singletonList(newAvatarUrl));
            Map<String, String> urlMappings = fileManagementService.confirmFiles(avatarUrls, BusinessType.USER_AVATAR, userId);

            // 如果URL发生了变化，更新用户信息中的头像URL
            if (!urlMappings.isEmpty()) {
                String mappedUrl = urlMappings.get(newAvatarUrl);
                if (mappedUrl != null && !mappedUrl.equals(newAvatarUrl)) {
                    UserInfo avatarUpdate = new UserInfo();
                    avatarUpdate.setId(userId);
                    avatarUpdate.setAvatarUrl(mappedUrl);
                    this.updateById(avatarUpdate);
                }
            }

            // 如果有旧头像且与新头像不同，将旧头像移到临时目录
            if (StringUtils.hasText(oldAvatarUrl) && !oldAvatarUrl.equals(newAvatarUrl)) {
                // 使用moveFilesToTemp代替deleteFiles
                fileManagementService.moveFilesToTemp(Collections.singletonList(oldAvatarUrl));
            }
        }
    }
}