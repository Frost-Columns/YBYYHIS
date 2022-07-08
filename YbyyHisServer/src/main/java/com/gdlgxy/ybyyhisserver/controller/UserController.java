package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.mapper.UserInfoMapper;
import com.gdlgxy.ybyyhisserver.pojo.RegisterGroup;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Consumer;

@RestController
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StatesMapper statesMapper;

    @PostMapping("/login")
    public ResultVO login(@RequestBody UserInfo user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("userpwd", user.getUserpwd());
        ResultVO resultVO = new ResultVO<>();
        if (userInfoMapper.selectByMap(map).size() > 0) {
            String token = DigestUtils.md5DigestAsHex((user.getUsername() + user.getUserpwd()).getBytes());
            return new ResultVO(200, "登录成功！", token, null);
        } else {
            return new ResultVO(201, "账号或密码错误！", null, null);
        }
    }

    @PostMapping("/userlist")
    public ResultVO getUserList(Integer index, String username, Integer roleid, Integer deptid, Integer[] checklist) {
        IPage<UserInfo> userInfoPage = new Page<>(index, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("role_id", roleid);
        map.put("dept_id", deptid);
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.allEq(map, false);
        wrapper.and(i -> i.like("username", username).or().like("usernamezh", username));
        if (checklist.length != 0) {
            wrapper.in("state", checklist);
        }else {
            wrapper.in("state", "");
        }
        return new ResultVO<>(200, "查询成功！", userInfoMapper.selectPage(userInfoPage, wrapper), null);
    }

    @PostMapping("/InsertUser")
    public ResultVO addUser(@RequestBody UserInfo userInfo) {
        userInfo.setUserpwd("123456");
        userInfo.setSalt("123456");
        userInfo.setSname("正常");
        System.out.println(userInfo);
        userInfoMapper.insert(userInfo);
        return new ResultVO(200, "用户添加成功！", true, null);
    }

    @PostMapping("/UpdateUser")
    public ResultVO updateUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        System.out.println(userInfoMapper.updateById(userInfo));
        return new ResultVO(200, "修改成功！", true, null);
    }

    @PostMapping("/FreezeUser")
    public ResultVO freezeUserUser(@RequestBody UserInfo userInfo) {
        return updateStateUser(userInfo,102);
    }

    @PostMapping("/ThawUser")
    public ResultVO thawUser(@RequestBody UserInfo userInfo) {
        return updateStateUser(userInfo,100);
    }

    @PostMapping("/CancellationUser")
    public ResultVO cancellationUser(@RequestBody UserInfo userInfo) {
        return updateStateUser(userInfo,101);
    }

    private ResultVO updateStateUser(UserInfo userInfo,Integer state){
        Map<String, Object> map = new HashMap<>();
        map.put("stateno", state);
        userInfo.setState(state);
        userInfo.setSname(statesMapper.selectByMap(map).get(0).getStatename());
        if (userInfoMapper.updateById(userInfo) == 1) {
            return new ResultVO(200, "修改成功！", true, null);
        } else {
            return new ResultVO(201, "修改失败！", false, null);
        }
    }

    @PostMapping("/UserGroupList")
    private ResultVO getUserGroupList(Integer dtId){
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.in("dt_id",dtId).in("role_id",2);
        return new ResultVO(201, "查询成功！", userInfoMapper.selectList(wrapper), null);
    }
}
