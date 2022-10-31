package com.ymchen.ranniservice.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymchen.ranni.component.datasource.annotation.DataPermission;
import com.ymchen.rannibase.entity.crm.User;

//@DataPermission(methods = {"selectList"},field = "store_id")
public interface UserMapper extends BaseMapper<User>{


}
