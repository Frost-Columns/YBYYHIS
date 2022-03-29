package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.CureMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.pojo.Case;
import com.gdlgxy.ybyyhisserver.pojo.Cure;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
