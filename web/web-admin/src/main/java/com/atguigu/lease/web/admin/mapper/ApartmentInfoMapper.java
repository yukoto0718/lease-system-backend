package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.model.entity.CityInfo;
import com.atguigu.lease.model.entity.DistrictInfo;
import com.atguigu.lease.model.entity.ProvinceInfo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    /**
     * 根据ID查询省份信息
     */
    ProvinceInfo selectProvinceById(Long id);

    /**
     * 根据ID查询城市信息
     */
    CityInfo selectCityById(Long id);

    /**
     * 根据ID查询区域信息
     */
    DistrictInfo selectDistrictById(Long id);
}




