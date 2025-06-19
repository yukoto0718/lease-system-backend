package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.FileManagement;
import com.atguigu.lease.model.enums.BusinessType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 文件管理服务接口
 */
public interface FileManagementService extends IService<FileManagement> {

    /**
     * 记录文件上传
     * @param originalFilename 原始文件名
     * @param path 存储路径
     * @param url 访问URL
     * @return 文件管理对象
     */
    FileManagement recordFileUpload(String originalFilename, String path, String url);

    /**
     * 确认文件使用
     * @param urls 文件URL列表
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 包含新旧URL映射的Map（key为旧URL，value为新URL）
     */
    Map<String, String> confirmFiles(List<String> urls, BusinessType businessType, Long businessId);

    /**
     * 清理过期临时文件
     * @param expireHours 过期小时数
     * @return 清理的文件数量
     */
    int clearExpiredTempFiles(int expireHours);

    /**
     * 删除文件
     * @param urls 文件URL列表
     * @return 是否删除成功
     */
    boolean deleteFiles(List<String> urls);

    /**
     * 将永久文件移回临时目录
     * 用于处理不再使用的图片，移动到临时目录后由定时任务清理
     * @param urls 文件URL列表
     * @return 包含新旧URL映射的Map（key为旧URL，value为新URL）
     */
    Map<String, String> moveFilesToTemp(List<String> urls);
}