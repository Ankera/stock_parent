package com.ankers.stock.service;

import com.ankers.stock.pojo.entity.SysUser;

public interface UserService {
    SysUser findByUsername(String username);
}
