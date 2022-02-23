package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdlgxy.ybyyhisserver.mapper.*;
import com.gdlgxy.ybyyhisserver.pojo.*;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private PermMapper permMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RolePermMapper rolePermMapper;

    @Autowired
    private StatesMapper statesMapper;

    @GetMapping("/RoleAndDeptList")
    public ResultVO getRoleAndDeptList(){
        Map<String, Object> map = new HashMap<>();
        map.put("depttype", deptTypeMapper.selectList(null));
        map.put("role", roleMapper.selectList(null));
        map.put("dept", deptMapper.selectList(null));
        return new ResultVO(200,"查询成功！",map,null);
    }

    @GetMapping("/DeptTypeList")
    public ResultVO getDeptTypeList(){
        return new ResultVO(200,"查询成功！",deptTypeMapper.selectList(null),null);
    }

    @PostMapping("/deptlist")
    public ResultVO getDeptList(Integer index, String dname, Integer dtId, Integer[] statelist){
        IPage<Dept> deptPage = new Page<>(index, 10);
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.like("dname", dname).in("dt_id", dtId);
        if (statelist.length != 0) {
            wrapper.in("state", statelist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", deptMapper.selectPage(deptPage, wrapper), null);
    }

    @PostMapping("/InsertDept")
    public ResultVO addDept(@RequestBody Dept dept) {
        dept.setSname("启用");
        deptMapper.insert(dept);
        return new ResultVO(200, "添加部门成功！", true, null);
    }

    @PostMapping("/UpdateDept")
    public ResultVO updateDept(@RequestBody Dept dept) {
        System.out.println(deptMapper.updateById(dept));
        return new ResultVO(200, "部门修改成功！", true, null);
    }

    @PostMapping("/FreezeDept")
    public ResultVO freezeDept(@RequestBody Dept dept) {
        return updateStateDept(dept,202);
    }

    @PostMapping("/ThawDept")
    public ResultVO thawDept(@RequestBody Dept dept) {
        return updateStateDept(dept,200);
    }

    @PostMapping("/CancellationDept")
    public ResultVO cancellationDept(@RequestBody Dept dept) {
        return updateStateDept(dept,201);
    }

    private ResultVO updateStateDept(Dept dept,Integer state){
        Map<String, Object> map = new HashMap<>();
        map.put("stateno", state);
        dept.setState(state);
        dept.setSname(statesMapper.selectByMap(map).get(0).getStatename());
        if (deptMapper.updateById(dept) == 1) {
            return new ResultVO(200, "修改成功！", true, null);
        } else {
            return new ResultVO(201, "修改失败！", false, null);
        }
    }

    @PostMapping("/RoleList")
    private ResultVO getRoleList(Integer[] statelist){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (statelist.length != 0) {
            wrapper.in("state", statelist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", roleMapper.selectList(wrapper), null);
    }

    @GetMapping("/PermAndMenuList")
    private ResultVO getPermAndMenuList(){
        Map<String, Object> map = new HashMap<>();
        map.put("perm", permMapper.selectList(null));
        map.put("menu", menuMapper.selectList(null));
        return new ResultVO(200,"查询成功！",map,null);
    }

    @PostMapping("/InsertRole")
    public ResultVO addRole(@RequestBody Role role) {
        role.setSname("启用");
        roleMapper.insert(role);
        return new ResultVO(200, "添加职位成功！", true, null);
    }

    @PostMapping("/UpdateRole")
    public ResultVO updateRole(@RequestBody Role role) {
        System.out.println(roleMapper.updateById(role));
        return new ResultVO(200, "职位修改成功！", true, null);
    }

    @PostMapping("/CancellationRole")
    public ResultVO cancellationRole(@RequestBody Role role) {
        Map<String, Object> map = new HashMap<>();
        map.put("stateno", 201);
        role.setState(201);
        role.setSname(statesMapper.selectByMap(map).get(0).getStatename());
        if (roleMapper.updateById(role) == 1) {
            return new ResultVO(200, "修改成功！", true, null);
        } else {
            return new ResultVO(201, "修改失败！", false, null);
        }
    }

    @PostMapping("/RolePerm")
    public ResultVO getRolePermList(@RequestBody Role role){
        QueryWrapper<RolePerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",role.getRoleid());
        return new ResultVO(200,"查询成功！",rolePermMapper.selectList(queryWrapper),null);
    }

    @PostMapping("/UpdateRolePerm")
    public ResultVO updateRolePerm(Integer[] rpList,Integer roleid){
        QueryWrapper<RolePerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",roleid);
        rolePermMapper.delete(queryWrapper);
        for(Integer rp:rpList){
            if(rp == null) continue;
            rolePermMapper.insert(new RolePerm(roleid,rp));
        }
        return new ResultVO(200, "修改成功！", true, null);
    }
}
