package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.PatientMapper;
import com.gdlgxy.ybyyhisserver.pojo.Patient;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PatientController {

    @Autowired
    private PatientMapper patientMapper;

    @PostMapping("/patientlist")
    public ResultVO getPatientList(Integer index, String pname){
        IPage<Patient> patientPage = new Page<>(index, 10);
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.like("pname",pname);
        return new ResultVO<>(200, "查询成功！", patientMapper.selectPage(patientPage, wrapper), null);
    }

    @PostMapping("/InsertPatient")
    public ResultVO addPatient(@RequestBody Patient patient) {
        patientMapper.insert(patient);
        return new ResultVO(200, "用户添加成功！", true, null);
    }

    @PostMapping("/UpdatePatient")
    public ResultVO updatePatient(@RequestBody Patient patient) {
        patientMapper.updateById(patient);
        return new ResultVO(200, "修改成功！", true, null);
    }
}
