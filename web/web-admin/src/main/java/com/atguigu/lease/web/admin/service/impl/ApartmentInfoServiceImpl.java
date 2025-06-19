package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.BusinessType;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private FileManagementService fileManagementService;//新增

    @Override
    @Transactional//追加事务处理注释
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId()!=null;
        // 保存旧图片URL用于后续处理
        List<String> oldImageUrls = new ArrayList<>();
        if (isUpdate){
            // 获取旧的图片列表
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphQueryWrapper.eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            List<GraphInfo> oldGraphInfos = graphInfoService.list(graphQueryWrapper);
            oldImageUrls = oldGraphInfos.stream().map(GraphInfo::getUrl).collect(Collectors.toList());

            //1.删除图片列表
            graphInfoService.remove(graphQueryWrapper);

            //2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = new LambdaQueryWrapper<>();
            facilityQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(facilityQueryWrapper);

            //3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = new LambdaQueryWrapper<>();
            labelQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(labelQueryWrapper);

            //4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = new LambdaQueryWrapper<>();
            feeQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(feeQueryWrapper);
        }

        // 补充省市区名称信息
        if (apartmentSubmitVo.getProvinceId() != null) {
            // 查询省份名称
            ProvinceInfo provinceInfo = baseMapper.selectProvinceById(apartmentSubmitVo.getProvinceId());
            if (provinceInfo != null) {
                apartmentSubmitVo.setProvinceName(provinceInfo.getName());
            }
        }

        if (apartmentSubmitVo.getCityId() != null) {
            // 查询城市名称
            CityInfo cityInfo = baseMapper.selectCityById(apartmentSubmitVo.getCityId());
            if (cityInfo != null) {
                apartmentSubmitVo.setCityName(cityInfo.getName());
            }
        }

        if (apartmentSubmitVo.getDistrictId() != null) {
            // 查询区域名称
            DistrictInfo districtInfo = baseMapper.selectDistrictById(apartmentSubmitVo.getDistrictId());
            if (districtInfo != null) {
                apartmentSubmitVo.setDistrictName(districtInfo.getName());
            }
        }

        // 保存主体数据
        super.saveOrUpdate(apartmentSubmitVo);

        //1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        List<String> newImageUrls = new ArrayList<>();//新增
        if (!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoList.add(graphInfo);
                // 新增 收集新图片URL
                newImageUrls.add(graphVo.getUrl());
            }

            // 确认图片状态（将临时图片标记为已确认使用）
            Map<String, String> urlMappings = fileManagementService.confirmFiles(newImageUrls, BusinessType.APARTMENT, apartmentSubmitVo.getId());

            // 更新graphInfo中的URL，确保与file_management中的URL一致
            if (!urlMappings.isEmpty()) {
                for (GraphInfo graphInfo : graphInfoList) {
                    String newUrl = urlMappings.get(graphInfo.getUrl());
                    if (newUrl != null) {
                        graphInfo.setUrl(newUrl);
                    }
                }
            }

            graphInfoService.saveBatch(graphInfoList);
        }

        //2.插入配套列表
        List<Long> facilityInfoIdList = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIdList)){
            ArrayList<ApartmentFacility> facilityList = new ArrayList<>();
            for (Long facilityId : facilityInfoIdList) {
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityId);
                facilityList.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityList);
        }

        //3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)) {
            List<ApartmentLabel> apartmentLabelList = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabelList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabelList);
        }

        //4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }

        // 处理不再使用的旧图片
        if (isUpdate && !CollectionUtils.isEmpty(oldImageUrls)) {
            // 找出旧图片中不在新图片列表的图片URL
            List<String> imagesToProcess = oldImageUrls.stream()
                    .filter(url -> !newImageUrls.contains(url))
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(imagesToProcess)) {
                // 将不再使用的图片移回temp目录，而不是直接删除
                Map<String, String> movedUrlMappings = fileManagementService.moveFilesToTemp(imagesToProcess);
            }
        }
    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        //1.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);

        //2.查询图片列表
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);

        //3.查询标签列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        //4.查询配套列表
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);

        //5.查询杂费列表
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(id);

        //6.组装结果
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);

        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        //由于公寓下会包含房间信息，因此在删除公寓时最好先判断一下该公寓下是否存在房间信息，若存在，则提醒用户先删除房间信息后再删除公寓信息
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId, id);
        Long count = roomInfoMapper.selectCount(roomInfoLambdaQueryWrapper);
        if (count > 0) {//待删除的公寓还有剩余房间，按业务逻辑，不能直接删除，需要提示用户。
            //直接为前端返回如下响应：先删除房间信息再删除公寓信息
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);//自定义异常，并被全局异常处理器捕获
        }

        // 获取所有图片URL
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphQueryWrapper.eq(GraphInfo::getItemId, id);
        List<GraphInfo> graphInfos = graphInfoService.list(graphQueryWrapper);
        List<String> imageUrls = graphInfos.stream().map(GraphInfo::getUrl).collect(Collectors.toList());

        // 先将所有图片移回temp目录
        if (!CollectionUtils.isEmpty(imageUrls)) {
            fileManagementService.moveFilesToTemp(imageUrls);
        }

        super.removeById(id);

        //删除关联的表记录，因为都是多对多的关系，所以删除的都是中间表记录。除了GraphInfo。
        //1.删除GraphInfo
        graphInfoService.remove(graphQueryWrapper);

        //2.删除ApartmentLabel
        LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = new LambdaQueryWrapper<>();
        labelQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(labelQueryWrapper);

        //3.删除ApartmentFacility
        LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = new LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(ApartmentFacility::getApartmentId, id);
        apartmentFacilityService.remove(facilityQueryWrapper);

        //4.删除ApartmentFeeValue
        LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = new LambdaQueryWrapper<>();
        feeQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(feeQueryWrapper);
    }
}