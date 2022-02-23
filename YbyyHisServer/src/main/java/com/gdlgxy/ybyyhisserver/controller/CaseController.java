package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.CaseMapper;
import com.gdlgxy.ybyyhisserver.pojo.Case;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CaseController {

    @Autowired
    private CaseMapper caseMapper;

    @PostMapping("/caselist")
    public ResultVO getCaseList(Integer index, String pname, Integer[] statelist){
        IPage<Case> casePage = new Page<>(index, 10);
        QueryWrapper<Case> wrapper = new QueryWrapper<>();
        wrapper.like("pname",pname);
        if (statelist.length != 0) {
            wrapper.in("state", statelist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", caseMapper.selectPage(casePage, wrapper), null);
    }
}
