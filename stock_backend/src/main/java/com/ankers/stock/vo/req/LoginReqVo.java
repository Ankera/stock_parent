package com.ankers.stock.vo.req;

import lombok.Data;

@Data
public class LoginReqVo {

    private String username;

    private String password;

    private String code;
}
