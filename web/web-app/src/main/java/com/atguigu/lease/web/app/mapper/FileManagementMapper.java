package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.FileManagement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 文件管理Mapper接口
 */
public interface FileManagementMapper extends BaseMapper<FileManagement> {

    /**
     * 查询过期的临时文件
     * @param expireTime 过期时间
     * @return 过期的文件列表
     */
    List<FileManagement> selectExpiredTempFiles(@Param("expireTime") Date expireTime);

    /**
     * 批量更新文件状态
     * @param ids 文件ID列表
     * @param businessType 业务类型
     * @param businessId 业务ID
     */
    void batchUpdateStatus(@Param("ids") List<Long> ids,
                           @Param("businessType") Integer businessType,
                           @Param("businessId") Long businessId);
}