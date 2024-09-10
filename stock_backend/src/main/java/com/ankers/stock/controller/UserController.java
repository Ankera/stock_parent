package com.ankers.stock.controller;

import com.ankers.stock.pojo.entity.SysUser;
import com.ankers.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // === @Controller + @ResponseBody
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public SysUser getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }
}
