package com.ankers.stock.service;

import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;

import java.util.Map;

public interface UserService {
     SysUser findByUsername(String username);

     R<LoginRespVo> login(LoginReqVo vo);

     R<Map> getCheckCode();
}
