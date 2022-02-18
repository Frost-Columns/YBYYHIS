package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.gdlgxy.ybyyhisserver.mapper.MenuMapper;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @PostMapping("/menu")
    public ResultVO getMenu(){
        return new ResultVO<>(200,"查询成功！",menuMapper.selectList(null),null);
    }
}
