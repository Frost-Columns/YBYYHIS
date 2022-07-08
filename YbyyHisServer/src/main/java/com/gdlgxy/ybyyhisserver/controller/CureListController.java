package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdlgxy.ybyyhisserver.mapper.CureLogsMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.pojo.CureLogs;
import com.gdlgxy.ybyyhisserver.pojo.Recipel;
import com.gdlgxy.ybyyhisserver.pojo.States;
import com.gdlgxy.ybyyhisserver.utils.FileUtils;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CureListController {
    @Autowired
    private CureLogsMapper cureLogsMapper;
    @Autowired
    private StatesMapper statesMapper;

    @PostMapping("/getCheckCureList")
    public ResultVO getCheckCureList(String pname) {
        Map<String,Object> map = new HashMap<>();
        map.put("curelogslist600",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",600)));
        map.put("curelogslist601",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",601)));
        map.put("curelogslist602",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",602)));
        return new ResultVO<>(200, "查询成功！", map, null);
    }

    @PostMapping("/getCureManaList")
    public ResultVO getCureManaList(String pname) {
        Map<String,Object> map = new HashMap<>();
        map.put("curelogslist1000",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",1000)));
        map.put("curelogslist1001",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",1001)));
        map.put("curelogslist1002",cureLogsMapper.selectList(new QueryWrapper<CureLogs>().like("pname", pname).in("state",1002)));
        return new ResultVO<>(200, "查询成功！", map, null);
    }

    @PostMapping("/CheckQy")
    public ResultVO checkQy(Integer clid) {
        CureLogs cureLogs = new CureLogs();
        cureLogs.setClid(clid);
        cureLogs.setState(601);
        cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 601)).get(0).getStatename());
        return new ResultVO<>(200, "修改成功！", cureLogsMapper.updateById(cureLogs), null);
    }

    @PostMapping("/CheckWc")
    public ResultVO checkWc(Integer clid) {
        CureLogs cureLogs = new CureLogs();
        cureLogs.setClid(clid);
        cureLogs.setState(602);
        cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 602)).get(0).getStatename());
        return new ResultVO<>(200, "修改成功！", cureLogsMapper.updateById(cureLogs), null);
    }

    @PostMapping("/CureQy")
    public ResultVO cureQy(Integer clid) {
        CureLogs cureLogs = new CureLogs();
        cureLogs.setClid(clid);
        cureLogs.setState(1001);
        cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 1001)).get(0).getStatename());
        return new ResultVO<>(200, "修改成功！", cureLogsMapper.updateById(cureLogs), null);
    }

    @PostMapping("/CureWc")
    public ResultVO cureWc(Integer clid) {
        CureLogs cureLogs = new CureLogs();
        cureLogs.setClid(clid);
        cureLogs.setState(1002);
        cureLogs.setSname(statesMapper.selectList(new QueryWrapper<States>().in("stateno", 1002)).get(0).getStatename());
        return new ResultVO<>(200, "修改成功！", cureLogsMapper.updateById(cureLogs), null);
    }

    @PostMapping("/updateCureLogsIcon")
    public ResultVO updateCureLogsIcon(@RequestParam Integer clid,@RequestParam("file") MultipartFile file) {
        CureLogs cureLogs = cureLogsMapper.selectById(clid);
        try {
            byte[] b = file.getBytes();
            String path = "E:\\server\\bs\\" + cureLogs.getClid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            FileUtils.writeByte(path, b);
            cureLogs.setCricon(path);
            cureLogs.setCureTime(new Timestamp(new Date().getTime()));
            cureLogsMapper.updateById(cureLogs);
            return new ResultVO<>(200, "修改成功！", cureLogsMapper.selectById(clid), null);
        } catch (IOException e) {
            return new ResultVO<>(201, "修改失败！", null, null);
        }
    }

    @PostMapping("/updateCureLogs")
    public ResultVO updateCureLogs(@RequestBody CureLogs curelogsinfo) {
        return new ResultVO<>(200, "修改成功！", cureLogsMapper.updateById(curelogsinfo), null);
    }
}
