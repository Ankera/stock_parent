package com.ankers.service.impl;

import com.ankers.mapper.SysUserMapper;
import com.ankers.pojo.entity.SysUser;
import com.ankers.service.UserService;
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
