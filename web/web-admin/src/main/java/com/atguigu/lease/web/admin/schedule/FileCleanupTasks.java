package com.atguigu.lease.web.admin.schedule;

import com.atguigu.lease.web.admin.service.FileManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileCleanupTasks {

    @Autowired
    private FileManagementService fileManagementService;

    /**
     * 每天凌晨2点执行，清理24小时前上传但未使用的临时文件
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupTempFiles() {
        log.info("开始清理过期临时文件");
        int cleanedCount = fileManagementService.clearExpiredTempFiles(24);
        log.info("清理完成，共删除{}个过期临时文件", cleanedCount);
    }
}