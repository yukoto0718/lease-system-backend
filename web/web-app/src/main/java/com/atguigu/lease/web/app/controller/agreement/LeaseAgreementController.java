package com.atguigu.lease.web.app.controller.agreement;

import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.app.service.LeaseAgreementService;
import com.atguigu.lease.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/agreement")
@Tag(name = "租约信息")
public class LeaseAgreementController {


    @Autowired
    private LeaseAgreementService leaseAgreementService;

    @Operation(summary = "保存或更新租约")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        leaseAgreement.setUserId(LoginUserHolder.getLoginUser().getUserId());
        leaseAgreementService.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "查询个人租约列表")
    @GetMapping("listItem")
    public Result<List<AgreementItemVo>> listItem() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        List<AgreementItemVo> result = leaseAgreementService.listItem(userId);
        return Result.ok(result);
    }

    @GetMapping("getDetailById")
    public Result<AgreementDetailVo> getDetailById(Long id) {
        AgreementDetailVo detail = leaseAgreementService.getDetailById(id);
        return Result.ok(detail);
    }
}

