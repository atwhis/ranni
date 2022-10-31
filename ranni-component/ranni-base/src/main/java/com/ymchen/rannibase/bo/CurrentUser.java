package com.ymchen.rannibase.bo;

import com.ymchen.rannibase.entity.crm.User;
import lombok.Data;

@Data
public class CurrentUser extends User {

    private String deptIds;
}
