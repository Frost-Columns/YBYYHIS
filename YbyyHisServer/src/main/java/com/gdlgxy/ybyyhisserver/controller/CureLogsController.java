package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.CureLogsMapper;
import com.gdlgxy.ybyyhisserver.mapper.CureMapper;
import com.gdlgxy.ybyyhisserver.mapper.PatientMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.pojo.Cure;
import com.gdlgxy.ybyyhisserver.pojo.CureLogs;
import com.gdlgxy.ybyyhisserver.pojo.Patient;
import com.gdlgxy.ybyyhisserver.pojo.States;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class CureLogsController {

    @Autowired
    private CureLogsMapper cureLogsMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private CureMapper cureMapper;
    @Autowired
    private StatesMapper statesMapper;

    @PostMapping("/curelogslist")
    public ResultVO getCureLogsList(Integer index, String name) {
        IPage<CureLogs> cureLogsPage = new Page<>(index, 10);
        QueryWrapper<CureLogs> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.like("pname",name).or().like("cname",name));
        return new ResultVO<>(200, "查询成功！", cureLogsMapper.selectPage(cureLogsPage, wrapper), null);
    }

    @PostMapping("/getCureLogs1")
    public ResultVO getCureLogs1(Integer caseid) {
        QueryWrapper<CureLogs> wrapper = new QueryWrapper<CureLogs>();
        wrapper.in("case_id", caseid).like("state",60);
        return new ResultVO<>(200, "查询成功！", cureLogsMapper.selectList(wrapper), null);
    }

    @PostMapping("/getCureLogs2")
    public ResultVO getCureLogs2(Integer caseid) {
        QueryWrapper<CureLogs> wrapper = new QueryWrapper<CureLogs>();
        wrapper.in("case_id", caseid).like("state",100);
        return new ResultVO<>(200, "查询成功！", cureLogsMapper.selectList(wrapper), null);
    }

    @PostMapping("/addCureLogs")
    public ResultVO addCureLogs(Integer[] cureids, Integer caseid, Integer patientid, Integer state) {
        Patient patient = patientMapper.selectById(patientid);
        for (int cureid : cureids) {
            Cure cure = cureMapper.selectById(cureid);
            CureLogs cureLogs = new CureLogs();
            cureLogs.setCaseId(caseid);
            cureLogs.setPatientId(patientid);
            cureLogs.setPname(patient.getPname());
            cureLogs.setCureId(cureid);
            cureLogs.setCname(cure.getCnamezh());
            cureLogs.setLoc(cure.getLoc());
            cureLogs.setCreateTime(new Timestamp(new Date().getTime()));
            if (state == 600) {
                cureLogs.setState(600);
                cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 600)).get(0).getStatename());
            } else {
                cureLogs.setState(1000);
                cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 1000)).get(0).getStatename());
            }
            cureLogsMapper.insert(cureLogs);
        }
        return new ResultVO<>(200, "添加成功！", null, null);
    }

    @PostMapping("/delCureLogs")
    public ResultVO deleteCureLogs(Integer clid) {
        QueryWrapper<CureLogs> wrapper = new QueryWrapper<>();
        wrapper.in("clid",clid);
        wrapper.and(i -> i.in("state",600).or().in("state",1000));
        if (cureLogsMapper.delete(wrapper) == 1) {
            return new ResultVO(200,"删除成功！",null,null);
        } else {
            return new ResultVO(201,"删除失败，该数据不存在或状态已更新！",null,null);
        }
    }

}
