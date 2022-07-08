package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.DosageMapper;
import com.gdlgxy.ybyyhisserver.mapper.DrugsMapper;
import com.gdlgxy.ybyyhisserver.pojo.*;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DrugsController {

    @Autowired
    private DrugsMapper drugsMapper;

    @Autowired
    private DosageMapper dosageMapper;

    @PostMapping("/DrugsListByName")
    public ResultVO getDrugsListByName(String drugsname){
        if (drugsname.equals(null) || drugsname.equals("")){
            return new ResultVO<>(200, "查询成功！", null, null);
        }else{
            QueryWrapper<Drugs> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("drugsnamezh",drugsname);
            return new ResultVO<>(200, "查询成功！", drugsMapper.selectList(queryWrapper), null);
        }
    }

    @PostMapping("/drugslist")
    public ResultVO getDrugsList(Integer index, String dname){
        IPage<Drugs> drugsPage = new Page<>(index, 10);
        QueryWrapper<Drugs> wrapper = new QueryWrapper<>();
        wrapper.in("state",700);
        wrapper.and(i -> i.like("drugsname", dname).or().like("drugsnamezh",dname).or().like("manufacturer",dname));
        return new ResultVO<>(200, "查询成功！", drugsMapper.selectPage(drugsPage, wrapper), null);
    }

    @GetMapping("/dosagelist")
    public ResultVO getDosageList(){
        return new ResultVO<>(200, "查询成功！", dosageMapper.selectList(null), null);
    }

    @PostMapping("/getDrugs")
    public ResultVO getDrugsById(Integer drugsid){
        return new ResultVO<>(200, "查询成功！", drugsMapper.selectById(drugsid), null);
    }

    @PostMapping("/InsertDrugs")
    public ResultVO addDrugs(@RequestBody Drugs drugs) {
        drugsMapper.insert(drugs);
        return new ResultVO(200, "添加药品成功！", true, null);
    }

    @PostMapping("/UpdateDrugs")
    public ResultVO updateDrugs(@RequestBody Drugs drugs) {
        drugsMapper.updateById(drugs);
        return new ResultVO(200, "药品信息修改成功！", true, null);
    }

    @PostMapping("/delDrugs")
    public ResultVO deleteDrugs(Integer drugsid) {
        Drugs drugs = new Drugs();
        drugs.setDrugsid(drugsid);
        drugs.setState(701);
        return new ResultVO(200,"删除成功!",drugsMapper.updateById(drugs),null);
    }

    @PostMapping("/delDrugsList")
    public ResultVO deleteDrugsList(@RequestBody Drugs[] dels) {
        System.out.println("dels -> " + dels);
        for (Drugs drugs : dels) {
            drugs.setState(701);
            drugsMapper.updateById(drugs);
        }
        return new ResultVO(200,"删除成功!",true,null);
    }

    @PostMapping("/getDrugeList")
    public ResultVO getDrugeList(String dname) {
        if (dname.equals("")) {
            dname = "null";
        }
        QueryWrapper<Drugs> wrapper = new QueryWrapper<>();
        wrapper.in("state",700);
        String finalDname = dname;
        if (dname.matches("^[0-9]*$")){
            wrapper.and(i -> i.like("drugsname", finalDname).or().like("drugsnamezh", finalDname).or().in("drugsid", Integer.parseInt(finalDname)));
        } else {
            wrapper.and(i -> i.like("drugsname", finalDname).or().like("drugsnamezh", finalDname));
        }
        return new ResultVO<>(200, "查询成功！", drugsMapper.selectList(wrapper), null);
    }

}
