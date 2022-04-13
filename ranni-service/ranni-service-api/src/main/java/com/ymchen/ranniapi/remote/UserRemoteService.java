package com.ymchen.ranniapi.remote;

import com.ymchen.ranniapi.remote.fallback.UserRemoteServiceFallback;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.crm.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_CRM, contextId = "userRemoteService", fallbackFactory = UserRemoteServiceFallback.class)
public interface UserRemoteService {

    @GetMapping("/user/getAllUser")
    List<User> getAllUser();

    @GetMapping("/user/getById")
    User getById(@RequestParam("userId") Integer userId);
}
