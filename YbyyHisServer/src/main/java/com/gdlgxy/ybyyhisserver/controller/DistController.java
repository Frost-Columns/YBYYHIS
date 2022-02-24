package com.gdlgxy.ybyyhisserver.controller;

import com.gdlgxy.ybyyhisserver.mapper.DistMapper;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistController {

    @Autowired
    private DistMapper distMapper;

    @PostMapping("/distname")
    public ResultVO getDistName(Integer distid){
        return new ResultVO<>(200, "查询成功！", distMapper.selectById(distid), null);
    }
}
