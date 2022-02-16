package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.gdlgxy.ybyyhisserver.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @PostMapping("/menu")
    public String getMenu(){
        return JSONObject.toJSONString(menuMapper.selectList(null));
    }
}
