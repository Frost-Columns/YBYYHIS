package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.*;
import com.gdlgxy.ybyyhisserver.pojo.*;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.nimbus.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CaseController {

    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private TreatmentLogsMapper treatmentLogsMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private CaseDistMapper caseDistMapper;
    @Autowired
    private DistMapper distMapper;
    @Autowired
    private StatesMapper statesMapper;

    @PostMapping("/caselist")
    public ResultVO getCaseList(Integer index, String pname, Integer[] statelist){
        IPage<Case> casePage = new Page<>(index, 10);
        QueryWrapper<Case> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.like("pname",pname).or().like("caseid",pname));
        if (statelist.length != 0) {
            wrapper.in("state", statelist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", caseMapper.selectPage(casePage, wrapper), null);
    }

    @PostMapping("/treatmentlogslist")
    public ResultVO getTreatmentLogsList(String pname) {
        Map<String,Object> map = new HashMap<>();
        map.put("treatmentlogslist500",treatmentLogsMapper.selectList(new QueryWrapper<TreatmentLogs>().like("pname", pname).in("state",500).last(" ORDER BY update_time")));
        map.put("treatmentlogslist501",treatmentLogsMapper.selectList(new QueryWrapper<TreatmentLogs>().like("pname", pname).in("state",501).last(" ORDER BY update_time")));
        map.put("treatmentlogslist502",treatmentLogsMapper.selectList(new QueryWrapper<TreatmentLogs>().like("pname", pname).in("state",502).last(" ORDER BY update_time")));
        return new ResultVO<>(200, "查询成功！", map, null);
    }

    @PostMapping("/getTreatmentInfo")
    public ResultVO getTreatmentInfo(Integer caseid) {
        Map<String, Object> map = new HashMap<>();
        Case caseinfo = caseMapper.selectById(caseid);
        Patient patientinfo = patientMapper.selectById(caseinfo.getPatientId());

        map.put("caseinfo", caseinfo);
        map.put("patientinfo", patientinfo);
        return new ResultVO<>(200, "查询成功！", map, null);
    }

    @PostMapping("/casedistlist")
    public  ResultVO getCaseDist(Integer caseid) {
        return new ResultVO<>(200, "查询成功！", caseDistMapper.selectList(new QueryWrapper<CaseDist>().in("case_id", caseid)), null);
    }

    @PostMapping("/addCaseDist")
    public ResultVO addCaseDist(CaseDist caseDist) {
        int i = 0;
        if (caseDist.getCaseId() != null && caseDist.getDistId() != null) {
            caseDist.setDname(distMapper.selectById(caseDist.getDistId()).getDistnamezh());
            i = caseDistMapper.insert(caseDist);
        }
        return new ResultVO<>(200, "插入成功！", i, null);
    }

    @PostMapping("/delCaseDist")
    public ResultVO deleteCaseDist(Integer cdid) {
        return new ResultVO<>(200, "删除成功！", caseDistMapper.deleteById(cdid), null);
    }

    @PostMapping("/updateCase")
    public ResultVO updateCase(@RequestBody Case c) {
        caseMapper.updateById(c);
        return new ResultVO<>(200, "删除成功！", caseMapper.selectById(c.getCaseid()), null);
    }

    @PostMapping("/updateTreatmentlogsQy")
    public ResultVO updateTreatmentlogsQy(Integer caseid, Integer tlid, Integer state) {
        Case c = new Case();
        c.setCaseid(caseid);
        c.setState(state);
        c.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", state)).get(0).getStatename());
        TreatmentLogs t = new TreatmentLogs();
        t.setTlid(tlid);
        t.setState(state);
        caseMapper.updateById(c);
        treatmentLogsMapper.updateById(t);
        return new ResultVO<>(200, "修改成功！", null, null);
    }

    @PostMapping("/addTreatmentlogs")
    public ResultVO getTreatmentLogsList(Integer patientId, String pname, Integer registerId) {
        Case caseinfo = new Case();
        caseinfo.setPatientId(patientId);
        caseinfo.setPname(pname);
        caseinfo.setHistoryOfTreatment("无");
        caseinfo.setPastHistory("无");
        caseMapper.insert(caseinfo);
        TreatmentLogs treatmentLogs = new TreatmentLogs();
        treatmentLogs.setRegisterId(registerId);
        treatmentLogs.setCaseId(caseinfo.getCaseid());
        treatmentLogs.setPname(pname);
        treatmentLogsMapper.insert(treatmentLogs);
        return new ResultVO<>(200, "预约成功！", null, null);
    }
}
