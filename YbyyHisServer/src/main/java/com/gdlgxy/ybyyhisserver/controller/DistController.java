package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdlgxy.ybyyhisserver.mapper.DistMapper;
import com.gdlgxy.ybyyhisserver.mapper.DistTypeMapper;
import com.gdlgxy.ybyyhisserver.pojo.Dist;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistController {

    @Autowired
    private DistMapper distMapper;

    @Autowired
    private DistTypeMapper distTypeMapper;

    @PostMapping("/distname")
    public ResultVO getDistName(Integer distid){
        return new ResultVO<>(200, "查询成功！", distMapper.selectById(distid), null);
    }

    @GetMapping("/disttypelist")
    public ResultVO getDistTypeList(){
        return new ResultVO<>(200, "查询成功！", distTypeMapper.selectList(null), null);
    }

    @PostMapping("/distlist")
    public ResultVO getDistList(Integer dtid){
        QueryWrapper<Dist> queryWrapper = new QueryWrapper();
        queryWrapper.in("dt_id",dtid);
        return new ResultVO<>(200, "查询成功！", distMapper.selectList(queryWrapper), null);
    }
}
