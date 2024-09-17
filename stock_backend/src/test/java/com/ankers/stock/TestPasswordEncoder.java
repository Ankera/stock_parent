package com.ankers.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TestPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder() {
        String pwd = "123456";

        System.out.println("加密字符串====>" + passwordEncoder.encode(pwd));
    }

    /**
     * @desc 测试匹配
     * 底层原理：
     * 从密文中获取盐值（随件码，参与密文生成的运算）后，利用盐值与明文密码进行加密得到密文，
     * 这个密文与输入的密文等值匹配
     */
    @Test
    public void testDecode() {
        String encodePwd="$2a$10$qfaFj6FX86CYsL5jB3nhVe2u3/44DmaTjyEgGi9yv5ofPx1uHYi5u";
        String pwd="123456";
        boolean isSuccess = passwordEncoder.matches(pwd, encodePwd);
        System.out.println(isSuccess?"匹配成功":"匹配失败");
    }
}
