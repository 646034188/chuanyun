package com.sankuai.mall.iot.chuanyunapi.api.controller;

import com.sankuai.meituan.auth.util.UserUtils;
import com.sankuai.meituan.auth.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/1")
    public User test1() {
        return UserUtils.getUser();
    }

    @RequestMapping("/2")
    public String test2() {
        return "test2";
    }
}
