package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.minio.MinioProperties;
import com.atguigu.lease.model.entity.FileManagement;
import com.atguigu.lease.model.enums.BusinessType;
import com.atguigu.lease.web.admin.mapper.FileManagementMapper;
import com.atguigu.lease.web.admin.service.FileManagementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

@Service
@Slf4j
public class FileManagementServiceImpl extends ServiceImpl<FileManagementMapper, FileManagement>
        implements FileManagementService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public FileManagement recordFileUpload(String originalFilename, String path, String url) {
        FileManagement fileManagement = new FileManagement();
        fileManagement.setOriginalFilename(originalFilename);
        fileManagement.setPath(path);
        fileManagement.setUrl(url);
        fileManagement.setStatus(0);// 临时状态
        save(fileManagement);
        return fileManagement;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> confirmFiles(List<String> urls, BusinessType businessType, Long businessId) {
        if (CollectionUtils.isEmpty(urls)) {
            return new HashMap<>();
        }

        // 用于返回旧URL到新URL的映射
        Map<String, String> urlMappings = new HashMap<>();

        // 查询对应的文件记录
        LambdaQueryWrapper<FileManagement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FileManagement::getUrl, urls);
        queryWrapper.eq(FileManagement::getStatus, 0);// 临时状态
        List<FileManagement> filesList = list(queryWrapper);

        if (filesList.isEmpty()) {
            return urlMappings;
        }

        // 移动文件从temp到permanent目录
        List<Long> updatedIds = new ArrayList<>();
        for (FileManagement file : filesList) {
            try {
                // 从URL中提取原对象名称 (temp/...)
                String tempObjectName = getObjectNameFromUrl(file.getUrl());

                // 如果路径包含temp/，才进行移动
                if (tempObjectName.startsWith("temp/")) {
                    // 创建新对象名称，将temp替换为permanent
                    String permanentObjectName = tempObjectName.replace("temp/", "permanent/");

                    // 首先复制文件
                    minioClient.copyObject(
                            CopyObjectArgs.builder()
                                    .bucket(minioProperties.getBucketName())
                                    .source(
                                            CopySource.builder()
                                                    .bucket(minioProperties.getBucketName())
                                                    .object(tempObjectName)
                                                    .build()
                                    )
                                    .object(permanentObjectName)
                                    .build()
                    );

                    // 然后删除旧文件
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(minioProperties.getBucketName())
                                    .object(tempObjectName)
                                    .build()
                    );

                    // 更新文件记录的路径和URL
                    String newUrl = file.getUrl().replace("/temp/", "/permanent/");

                    // 记录URL映射关系
                    urlMappings.put(file.getUrl(), newUrl);

                    // 单独更新这个文件的路径和URL
                    LambdaUpdateWrapper<FileManagement> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(FileManagement::getId, file.getId());
                    updateWrapper.set(FileManagement::getPath, permanentObjectName);
                    updateWrapper.set(FileManagement::getUrl, newUrl);
                    update(updateWrapper);
                }

                // 添加到要更新状态的ID列表
                updatedIds.add(file.getId());

            } catch (Exception e) {
                log.error("移动文件失败: " + file.getUrl(), e);
                // 记录错误但继续处理其他文件
            }
        }

        // 批量更新文件状态（如果有文件成功移动）
        if (!updatedIds.isEmpty()) {
            baseMapper.batchUpdateStatus(updatedIds, businessType.getCode(), businessId);
        }

        return urlMappings;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int clearExpiredTempFiles(int expireHours) {
        // 计算过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -expireHours);
        Date expireTime = calendar.getTime();

        // 查询过期的临时文件
        List<FileManagement> expiredFiles = baseMapper.selectExpiredTempFiles(expireTime);

        if (expiredFiles.isEmpty()) {
            return 0;
        }

        // 从MinIO中删除文件
        for (FileManagement file : expiredFiles) {
            try {
                String objectName = getObjectNameFromUrl(file.getUrl());
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioProperties.getBucketName())
                                .object(objectName)
                                .build()
                );
            } catch (Exception e) {
                log.error("删除MinIO文件失败: " + file.getUrl(), e);
                // 继续处理其他文件
            }
        }

        // 从数据库中删除记录
        List<Long> expiredIds = expiredFiles.stream()
                .map(FileManagement::getId)
                .collect(Collectors.toList());
        removeByIds(expiredIds);

        return expiredFiles.size();
    }

    @Override
    public boolean deleteFiles(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return true;
        }

        // 查询文件记录
        LambdaQueryWrapper<FileManagement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FileManagement::getUrl, urls);
        List<FileManagement> filesList = list(queryWrapper);

        if (filesList.isEmpty()) {
            return true;
        }

        // 从MinIO中删除文件
        for (FileManagement file : filesList) {
            try {
                String objectName = getObjectNameFromUrl(file.getUrl());
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioProperties.getBucketName())
                                .object(objectName)
                                .build()
                );
            } catch (Exception e) {
                log.error("删除MinIO文件失败: " + file.getUrl(), e);
                // 继续处理其他文件
            }
        }

        // 从数据库中删除记录
        List<Long> ids = filesList.stream()
                .map(FileManagement::getId)
                .collect(Collectors.toList());
        return removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> moveFilesToTemp(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return new HashMap<>();
        }

        // 用于返回旧URL到新URL的映射
        Map<String, String> urlMappings = new HashMap<>();

        // 查询对应的文件记录
        LambdaQueryWrapper<FileManagement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FileManagement::getUrl, urls);
        queryWrapper.eq(FileManagement::getStatus, 1); // 已确认状态
        List<FileManagement> filesList = list(queryWrapper);

        if (filesList.isEmpty()) {
            return urlMappings;
        }

        // 移动文件从permanent到temp目录
        for (FileManagement file : filesList) {
            try {
                // 从URL中提取原对象名称 (permanent/...)
                String permanentObjectName = getObjectNameFromUrl(file.getUrl());

                // 如果路径包含permanent/，才进行移动
                if (permanentObjectName.startsWith("permanent/")) {
                    // 创建新对象名称，将permanent替换为temp
                    String tempObjectName = permanentObjectName.replace("permanent/", "temp/");

                    // 首先复制文件
                    minioClient.copyObject(
                            CopyObjectArgs.builder()
                                    .bucket(minioProperties.getBucketName())
                                    .source(
                                            CopySource.builder()
                                                    .bucket(minioProperties.getBucketName())
                                                    .object(permanentObjectName)
                                                    .build()
                                    )
                                    .object(tempObjectName)
                                    .build()
                    );

                    // 然后删除旧文件
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(minioProperties.getBucketName())
                                    .object(permanentObjectName)
                                    .build()
                    );

                    // 更新文件记录的路径和URL
                    String newUrl = file.getUrl().replace("/permanent/", "/temp/");

                    // 记录URL映射关系
                    urlMappings.put(file.getUrl(), newUrl);

                    // 更新数据库中的路径、URL和状态
                    LambdaUpdateWrapper<FileManagement> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(FileManagement::getId, file.getId());
                    updateWrapper.set(FileManagement::getPath, tempObjectName);
                    updateWrapper.set(FileManagement::getUrl, newUrl);
                    updateWrapper.set(FileManagement::getStatus, 0); // 设置为临时状态
                    updateWrapper.set(FileManagement::getBusinessType, null); // 清除业务类型
                    updateWrapper.set(FileManagement::getBusinessId, null); // 清除业务ID
                    updateWrapper.set(FileManagement::getUpdateTime, new Date()); // 更新时间
                    update(updateWrapper);
                }
            } catch (Exception e) {
                log.error("移动文件从permanent到temp失败: " + file.getUrl(), e);
                // 记录错误但继续处理其他文件
            }
        }

        return urlMappings;
    }

    /**
     * 从URL中提取对象名称
     */
    private String getObjectNameFromUrl(String url) {
        if (!StringUtils.hasText(url)) {
            return "";
        }
        // 示例URL: http://192.168.23.101:9000/mobile-lease/temp/20250409/uuid-filename.jpg
        String bucketName = minioProperties.getBucketName();
        int bucketIndex = url.indexOf(bucketName);
        if (bucketIndex == -1) {
            return url;// 返回原始URL作为回退
        }
        return url.substring(bucketIndex + bucketName.length() + 1);
    }
}