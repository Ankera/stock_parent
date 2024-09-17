package com.ankers.stock.service.impl;

import com.ankers.stock.mapper.SysUserMapper;
import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;
import com.ankers.stock.vo.resp.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findUserInfoByUsername(username);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        if (vo == null ||
                StringUtils.isBlank(vo.getUsername()) ||
                StringUtils.isBlank(vo.getPassword()) ||
                StringUtils.isBlank(vo.getCode())) {

                return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
            }

        SysUser user = findByUsername(vo.getUsername());
        if (user == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }

        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }

        LoginRespVo respVo = new LoginRespVo();
        System.out.println("==respVo===>" + respVo);

        BeanUtils.copyProperties(user, respVo);
        return R.ok(respVo);
    }
}
