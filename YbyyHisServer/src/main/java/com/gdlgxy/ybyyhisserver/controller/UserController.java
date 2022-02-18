package com.gdlgxy.ybyyhisserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.UserInfoMapper;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/login")
    public ResultVO login(@RequestBody UserInfo user){
        Map<String,Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("userpwd", user.getUserpwd());
        ResultVO resultVO = new ResultVO<>();
        if(userInfoMapper.selectByMap(map).size()>0){
            String token = DigestUtils.md5DigestAsHex((user.getUsername()+user.getUserpwd()).getBytes());
            return new ResultVO(200, "登录成功！", token, null);
        }else{
            return new ResultVO(201, "账号或密码错误！", null, null);
        }
    }

    @GetMapping("/userlist/{index}")
    public ResultVO getUserList(@PathVariable("index") Integer index){
        IPage<UserInfo> userInfoPage = new Page<>(index,10);
        return new ResultVO<>(200,"查询成功！",userInfoMapper.selectPage(userInfoPage, null),null);
    }

    @PostMapping("/InsertUser")
    public ResultVO addUser(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        return new ResultVO(200,"",true,null);
    }

    @PostMapping("/UpdateUser")
    public ResultVO updateUser(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        return new ResultVO(200,"",true,null);
    }
}
