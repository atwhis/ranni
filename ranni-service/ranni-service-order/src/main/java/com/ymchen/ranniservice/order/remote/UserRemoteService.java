package com.ymchen.ranniservice.order.remote;


import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.crm.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_CRM, contextId = "order-userRemoteService")
public interface UserRemoteService {

    @GetMapping("/user/getById")
    User getById(@RequestParam("userId") Integer userId);
}
