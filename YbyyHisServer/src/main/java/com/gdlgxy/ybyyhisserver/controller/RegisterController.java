package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.RegisterGroupMapper;
import com.gdlgxy.ybyyhisserver.mapper.RegisterMapper;
import com.gdlgxy.ybyyhisserver.mapper.RegisterRuleMapper;
import com.gdlgxy.ybyyhisserver.mapper.UserInfoMapper;
import com.gdlgxy.ybyyhisserver.pojo.Register;
import com.gdlgxy.ybyyhisserver.pojo.RegisterGroup;
import com.gdlgxy.ybyyhisserver.pojo.RegisterRule;
import com.gdlgxy.ybyyhisserver.pojo.UserInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class RegisterController {

    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private RegisterGroupMapper registerGroupMapper;
    @Autowired
    private RegisterRuleMapper registerRuleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/registerGroup")
    public ResultVO getRegisterGroup(Integer index, Integer dtId) {
        IPage<RegisterGroup> registerGroupPage = new Page<>(index, 10);
        QueryWrapper<RegisterGroup> wrapper = new QueryWrapper<>();
        if (dtId != null && dtId != 0) {
            wrapper.in("dt_id",dtId);
            return new ResultVO<>(200, "查询成功！", registerGroupMapper.selectPage(registerGroupPage, wrapper), null);
        } else {
            return new ResultVO<>(200, "查询成功！", null, null);
        }
    }

    @PostMapping("/updateGroup")
    public ResultVO updateGroup(@RequestBody RegisterRule[] rules) {
        for (RegisterRule registerRule : rules) {
            if (registerRule.getRrid() == null) {
                registerRuleMapper.insert(registerRule);
            } else {
                registerRuleMapper.updateById(registerRule);
            }
        }
        return new ResultVO<>(200, "修改成功！", null, null);
    }

    @PostMapping("/addGroup")
    public ResultVO addGroup(@RequestBody RegisterGroup group) {
        return new ResultVO<>(200, "添加成功！", registerGroupMapper.insert(group), null);
    }

    @PostMapping("/RegisterRuleList")
    public ResultVO getRegisterRuleList(@RequestBody RegisterGroup group) {
        return new ResultVO<>(200, "查询成功！", registerRuleMapper.selectList(new QueryWrapper<RegisterRule>().in("rg_id",group.getRgid())), null);
    }

    @PostMapping("/addRegister")
    public ResultVO addRegister(Integer rgid, Date time1, Date time2) {
        List<RegisterRule> rules = registerRuleMapper.selectList(new QueryWrapper<RegisterRule>().in("rg_id",rgid));
        List<Date> times = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        while(true) {
            if (calendar1.getTime().after(time2)) {
                times.add(calendar1.getTime());
                calendar1.add(Calendar.DATE, -1);
                continue;
            }
            times.add(calendar1.getTime());
            break;
        }
        for (RegisterRule rule : rules) {
            UserInfo user = userInfoMapper.selectById(rule.getUserId());
            for (Date time : times) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(time);
                switch (cal.get(Calendar.DAY_OF_WEEK)-1) {
                    case 0://  周日
                        if (rule.getSundayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getSundayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 1: //  周一
                        if (rule.getMondayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getMondayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 2:
                        if (rule.getTuesdayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getTuesdayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 3:
                        if (rule.getWednesdayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getWednesdayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 4:
                        if (rule.getThursdayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getThursdayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 5:
                        if (rule.getFridayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getFridayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                    case 6:
                        if (rule.getSaturdayAm()) {
                            insertRegister(user, rule,0, time);
                        }
                        if (rule.getSaturdayPm()) {
                            insertRegister(user, rule,1, time);
                        }
                        break;
                }
            }
        }

        return new ResultVO<>(200, "添加成功！", null, null);
    }

    private void insertRegister(UserInfo user, RegisterRule rule,Integer meridem, Date time){
        Register register = new Register();
        register.setDeptId(user.getDeptId());
        register.setUserId(rule.getUserId());
        register.setRlId(rule.getRlId());
        register.setCreateTime(new Timestamp(time.getTime()));
        register.setMeridiem(meridem);
        register.setSurcount(rule.getCount());
        QueryWrapper<Register> wrapper = new QueryWrapper<>();
        wrapper.in("user_id",rule.getUserId()).in("create_time",new Timestamp(time.getTime())).in("meridiem",meridem).in("rl_id",rule.getRlId());
        List<Register> rs = registerMapper.selectList(wrapper);
        if (rs.size() > 0) {
            register.setRegisterid(rs.get(0).getRegisterid());
            registerMapper.updateById(register);
        } else {
            registerMapper.insert(register);
        }
    }

    @PostMapping("/getRegisterList")
    private ResultVO getRegister(Integer deptId) {
        return new ResultVO<>(200, "查询成功！", registerMapper.selectList(new QueryWrapper<Register>().in("dept_id",deptId)), null);
    }
}
