package com.sankuai.mall.iot.chuanyunapi.api.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
  public class WebController {

    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String indexWeb() {
        return "index";
    }


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
      return "index";
    }

}
