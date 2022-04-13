package com.ymchen.ranniservice.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymchen.rannibase.entity.crm.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getById(Integer userId);

    List<User> getAllUser();
}