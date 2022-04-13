package com.ymchen.ranniservice.crm.controller;


import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.ranniservice.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@RefreshScope
public class UserController {

    private final UserService userService;


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
