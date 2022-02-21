package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.gdlgxy.ybyyhisserver.mapper.DeptMapper;
import com.gdlgxy.ybyyhisserver.mapper.DeptTypeMapper;
import com.gdlgxy.ybyyhisserver.mapper.RoleMapper;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RDController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DeptTypeMapper deptTypeMapper;

    @Autowired
    private DeptMapper deptMapper;

    @GetMapping("/RoleAndDeptList")
    public ResultVO getRoleAndDeptList(){
        Map<String, Object> map = new HashMap<>();
        map.put("depttype", deptTypeMapper.selectList(null));
        map.put("role", roleMapper.selectList(null));
        map.put("dept", deptMapper.selectList(null));
        return new ResultVO(200,"查询成功！",map,null);
    }
}
