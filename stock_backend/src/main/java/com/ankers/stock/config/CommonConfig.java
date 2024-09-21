package com.ankers.stock.config;

import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(StockInfoConfig.class) // 开启配置，随用谁配置
public class CommonConfig {

    /**
     * 定义密码加密匹配器bean
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置id生成器
     */
    @Bean
    public IdWorker idWorker(){
        //基于2号机房的1号机器生成的id
        return new IdWorker(1L, 2L);
    }
}
