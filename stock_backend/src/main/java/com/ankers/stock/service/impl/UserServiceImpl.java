package com.ankers.stock.service.impl;

import cn.hutool.captcha.LineCaptcha;
import com.ankers.stock.mapper.SysUserMapper;
import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import com.ankers.stock.utils.IdWorker;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;
import com.ankers.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

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

        BeanUtils.copyProperties(user, respVo);
        return R.ok(respVo);
    }

    @Override
    public R<Map> getCheckCode() {
        LineCaptcha lineCaptcha = new LineCaptcha(250, 40, 4, 5);
        lineCaptcha.setBackground(Color.LIGHT_GRAY);
//        lineCaptcha.setGenerator();
        String code = lineCaptcha.getCode();
        String imageData = lineCaptcha.getImageBase64();
        String sessionId = String.valueOf(idWorker.nextId());

        log.info("当前生产的图片校验码：{}， 会话ID：{}", code, sessionId);
        redisTemplate.opsForValue().set("CK:"+sessionId, code, 5, TimeUnit.MINUTES);

        Map<String, String> data = new HashMap<>();
        data.put("imageData", imageData);
        data.put("sessionId", sessionId);
        return R.ok(data);
    }
}
