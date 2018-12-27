package com.sankuai.mall.iot.chuanyunapi.api.controller;

import com.sankuai.meituan.auth.util.UserUtils;
import com.sankuai.meituan.auth.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/user")
    public User getUser() {
        return UserUtils.getUser();
    }

}
