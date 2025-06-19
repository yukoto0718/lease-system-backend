package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.FileManagement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FileManagementMapper extends BaseMapper<FileManagement> {

    /**
     * 查询指定时间之前的临时文件
     *@paramexpireTime 过期时间
     *@return 文件列表
     */
    List<FileManagement> selectExpiredTempFiles(@Param("expireTime") Date expireTime);

    /**
     * 批量更新文件状态
     *@paramids 文件ID列表
     *@parambusinessType 业务类型
     *@parambusinessId 业务ID
     *@return 影响行数
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids,
                          @Param("businessType") Integer businessType,
                          @Param("businessId") Long businessId);
}
