package com.ankers.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfig {

    /**
     * 定义密码加密匹配器bean
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置id生成器
     * @return
     */
//    @Bean
//    public IdWorker idWorker(){
//        //基于2号机房的1号机器生成的id
//        return new IdWorker(1l,2l);
//    }
}
