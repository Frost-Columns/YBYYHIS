package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.CureLogsMapper;
import com.gdlgxy.ybyyhisserver.mapper.CureMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.pojo.Case;
import com.gdlgxy.ybyyhisserver.pojo.Cure;
import com.gdlgxy.ybyyhisserver.pojo.CureLogs;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CureController {

    @Autowired
    private CureMapper cureMapper;
    @Autowired
    private StatesMapper statesMapper;

    @ResponseBody
    @PostMapping("/curelist")
    public ResultVO getCureList(Integer index, String cname, Integer[] statelist) {
        IPage<Cure> curePage = new Page<>(index, 10);
        QueryWrapper<Cure> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.like("cname",cname).or().like("cnamezh",cname));
        if (statelist.length != 0) {
            wrapper.in("state", statelist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", cureMapper.selectPage(curePage, wrapper), null);
    }

    @PostMapping("/getCureList")
    public ResultVO getCureList(String cname, Integer state){
        if (cname != null && !cname.equals("")) {
            QueryWrapper<Cure> wrapper = new QueryWrapper<>();
            wrapper.and(i -> i.like("cname", cname).or().like("cnamezh",cname));
            if (state == 600) {
                wrapper.in("state", 600);
            } else {
                wrapper.in("state", 1000);
            }
            return new ResultVO<>(200, "查询成功！", cureMapper.selectList(wrapper), null);
        } else {
            return new ResultVO<>(200, "查询成功！", null, null);
        }
    }

    @PostMapping("/getCaseById")
    public ResultVO getCaseById(Integer cureid){
        return new ResultVO<>(200, "查询成功！", cureMapper.selectById(cureid), null);
    }

    @PostMapping("/InsertCure")
    public ResultVO addDept(@RequestBody Cure cure) {
        cure.setSname("启用");
        cureMapper.insert(cure);
        return new ResultVO(200, "添加项目成功！", true, null);
    }

    @PostMapping("/UpdateCure")
    public ResultVO updateDept(@RequestBody Cure cure) {
        System.out.println(cureMapper.updateById(cure));
        return new ResultVO(200, "项目修改成功！", true, null);
    }

    @PostMapping("/ThawCure")
    public ResultVO thawCure(@RequestBody Cure cure) {
        return updateStateCure(cure,200);
    }

    @PostMapping("/CancellationCure")
    public ResultVO cancellationCure(@RequestBody Cure cure) {
        return updateStateCure(cure,201);
    }

    private ResultVO updateStateCure(Cure cure,Integer state){
        Map<String, Object> map = new HashMap<>();
        map.put("stateno", state);
        cure.setState(state);
        cure.setSname(statesMapper.selectByMap(map).get(0).getStatename());
        if (cureMapper.updateById(cure) == 1) {
            return new ResultVO(200, "修改成功！", true, null);
        } else {
            return new ResultVO(201, "修改失败！", false, null);
        }
    }
}
