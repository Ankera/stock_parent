package com.ankers.stock.controller;

import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import com.ankers.stock.vo.req.LoginReqVo;
import com.ankers.stock.vo.resp.LoginRespVo;
import com.ankers.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "用户相关接口服务")
@RestController // === @Controller + @ResponseBody
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据用户名查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", dataType = "String", required = true, type = "path")
    })
    @GetMapping("/user/info")
    public R<Object> getUserByUsername(String name) {
        SysUser user = userService.findByUsername("admin");
        return R.ok("查询成功", user);
    }

    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }

    @ApiOperation("生成图片获取验证码")
    @GetMapping("/captcha")
    public R<Map> getCheckCode(){
        return userService.getCheckCode();
    }
}
