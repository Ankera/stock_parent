package com.ankers.stock.service.impl;

import com.ankers.stock.mapper.SysUserMapper;
import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findUserInfoByUsername(username);
    }
}
