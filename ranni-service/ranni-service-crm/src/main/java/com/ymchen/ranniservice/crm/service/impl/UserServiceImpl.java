package com.ymchen.ranniservice.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.ranniservice.crm.mapper.UserMapper;
import com.ymchen.ranniservice.crm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getById(Integer userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    public List<User> getAllUser() {
        return baseMapper.selectList(null);
    }
}
