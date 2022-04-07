package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.CureLogsMapper;
import com.gdlgxy.ybyyhisserver.pojo.Case;
import com.gdlgxy.ybyyhisserver.pojo.CureLogs;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CureLogsController {

    @Autowired
    private CureLogsMapper cureLogsMapper;

    @PostMapping("/curelogslist")
    public ResultVO getCureLogsList(Integer index, String name) {
        IPage<CureLogs> cureLogsPage = new Page<>(index, 10);
        QueryWrapper<CureLogs> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.like("pname",name).or().like("cname",name));
        return new ResultVO<>(200, "查询成功！", cureLogsMapper.selectPage(cureLogsPage, wrapper), null);
    }

}
