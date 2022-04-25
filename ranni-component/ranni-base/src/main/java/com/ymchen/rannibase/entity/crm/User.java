package com.ymchen.rannibase.entity.crm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 7380838072642964578L;

    @TableId(value ="id",type = IdType.AUTO)
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_phone")
    private String userPhone;

    @TableField("user_address")
    private String userAddress;
}
