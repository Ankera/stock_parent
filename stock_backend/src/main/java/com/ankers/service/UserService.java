package com.ankers.service;

import com.ankers.pojo.entity.SysUser;

public interface UserService {

    SysUser findByUsername(String username);
}
