package com.sankuai.mall.iot.chuanyunapi.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/monitor")
public class AliveController {

    @RequestMapping("/alive")
    public String hello() {
        return "com.sankuai.mall.cart.platform is alive!";
    }
}
