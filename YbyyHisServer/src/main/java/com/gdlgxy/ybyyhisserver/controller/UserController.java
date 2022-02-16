package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.UserInfoMapper;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/login")
    public Object login(@RequestBody UserInfo user){
        Map<String,Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("userpwd", user.getUserpwd());
        if(userInfoMapper.selectByMap(map).size()>0){
            String token = DigestUtils.md5DigestAsHex((user.getUsername()+user.getUserpwd()).getBytes());
            map = new HashMap<>();
            map.put("token",token);
            map.put("message",true);
        }else{
            map = new HashMap<>();
            map.put("token",null);
            map.put("message",false);
        }
        return map;
    }

    @GetMapping("/userlist/{index}")
    public String getUserList(@PathVariable("index") Integer index){
        IPage<UserInfo> userInfoPage = new Page<>(index,10);
        return JSONObject.toJSONString(userInfoMapper.selectPage(userInfoPage, null));
    }
}
