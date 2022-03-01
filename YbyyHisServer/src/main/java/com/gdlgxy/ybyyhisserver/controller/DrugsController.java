package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdlgxy.ybyyhisserver.mapper.DrugsMapper;
import com.gdlgxy.ybyyhisserver.pojo.Drugs;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugsController {

    @Autowired
    private DrugsMapper drugsMapper;

    @PostMapping("/DrugsListByName")
    public ResultVO getDrugsListByName(String drugsname){
        if (drugsname.equals(null) || drugsname.equals("")){
            return new ResultVO<>(200, "查询成功！", null, null);
        }else{
            QueryWrapper<Drugs> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("drugsnamezh",drugsname);
            return new ResultVO<>(200, "查询成功！", drugsMapper.selectList(queryWrapper), null);
        }
    }

    @PostMapping("/getDrugs")
    public ResultVO getDrugsById(Integer drugsid){
        return new ResultVO<>(200, "查询成功！", drugsMapper.selectById(drugsid), null);
    }
}
