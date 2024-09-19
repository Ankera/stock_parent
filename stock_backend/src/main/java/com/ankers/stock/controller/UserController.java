package com.ankers.stock.controller;

import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // === @Controller + @ResponseBody
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/info")
    public R<Object> getUserByUsername() {
        SysUser user = userService.findByUsername("admin");
        return R.ok("查询成功", user);
    }

    @PostMapping("/user/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }

    @GetMapping("/captcha")
    public R<Map> getCheckCode(){
        return userService.getCheckCode();
    }
}
