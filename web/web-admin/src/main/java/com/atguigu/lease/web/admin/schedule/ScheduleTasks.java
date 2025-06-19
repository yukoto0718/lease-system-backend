package com.atguigu.lease.web.admin.schedule;

import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * SpringBoot内置了定时任务，在SpringBoot启动类上增加`@EnableScheduling`注解开启定时任务，
 * 在具体的定时任务方法上加上`@Scheduled(cron = "0 0 0 * * *")`规定定时执行的具体时间。
 */
@Component
public class ScheduleTasks {

    @Autowired
    private LeaseAgreementService service;

    @Scheduled(cron = "0 0 0 * * *") //编写定时任务的规则
    public void checkLeaseStatus() {
        //核心逻辑是，比较租约信息表中记录的lease_end_date字段的值与当前日期值，如果超过，则更新。
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        //le 筛选小于等于的， ge 筛选大于等于的
        updateWrapper.le(LeaseAgreement::getLeaseEndDate, new Date());//结束时间小于等于当前日期。即当前时间大于等于租约过期时间。
        updateWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);//筛选条件为已签约和退租待确认。
        updateWrapper.set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED);//租约状态更新为已过期。
        service.update(updateWrapper);//实际更新。
    }
/*
//测试-在控制台打印输出每一分每一秒
    @Scheduled(cron = "* * * * * *")
    public void test() {
        System.out.println(new Date());
    }
 */


}
