package com.ymchen.ranniservice.crm.controller;


import com.ymchen.rannibase.entity.base.PageRequest;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.ranniservice.crm.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@RefreshScope
public class UserController {

    private final UserService userService;

    @ApiOperation("分页查询用户-测试分页插件")
    @PostMapping
    public Object getByPages(PageRequest pageRequest, User user) {
        return userService.getByPage(pageRequest, user);
    }

    @GetMapping("getById")
    public User getById(@RequestParam("userId") Integer userId) {
        User user = userService.getById(userId);
        return user;
    }

    @GetMapping("getAllUser")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
