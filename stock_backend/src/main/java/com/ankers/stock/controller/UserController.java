package com.ankers.stock.controller;

import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // === @Controller + @ResponseBody
//@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public SysUser getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/user/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }
}
