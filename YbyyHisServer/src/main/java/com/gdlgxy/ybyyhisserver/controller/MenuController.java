package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdlgxy.ybyyhisserver.mapper.MenuMapper;
import com.gdlgxy.ybyyhisserver.mapper.RoleMenuMapper;
import com.gdlgxy.ybyyhisserver.pojo.Role;
import com.gdlgxy.ybyyhisserver.pojo.RoleMenu;
import com.gdlgxy.ybyyhisserver.pojo.RolePerm;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @PostMapping("/menu")
    public ResultVO getMenu(){
        return new ResultVO<>(200,"查询成功！",menuMapper.selectList(null),null);
    }

    @PostMapping("/RoleMenu")
    public ResultVO getRoleMenuList(@RequestBody Role role){
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",role.getRoleid());
        return new ResultVO(200,"查询成功！",roleMenuMapper.selectList(queryWrapper),null);
    }

    @PostMapping("/UpdateRoleMenu")
    public ResultVO updateRoleMenu(Integer[] rmList,Integer roleid){
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",roleid);
        roleMenuMapper.delete(queryWrapper);
        for(Integer rm:rmList){
            if(rm == null) continue;
            roleMenuMapper.insert(new RoleMenu(roleid,rm));
        }
        return new ResultVO(200, "修改成功！", true, null);
    }
}
