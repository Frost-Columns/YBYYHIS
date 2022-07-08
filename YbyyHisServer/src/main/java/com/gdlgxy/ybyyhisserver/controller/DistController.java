package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdlgxy.ybyyhisserver.mapper.DistMapper;
import com.gdlgxy.ybyyhisserver.mapper.DistTypeMapper;
import com.gdlgxy.ybyyhisserver.pojo.Dept;
import com.gdlgxy.ybyyhisserver.pojo.Dist;
import com.gdlgxy.ybyyhisserver.pojo.DistType;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistController {

    @Autowired
    private DistMapper distMapper;

    @Autowired
    private DistTypeMapper distTypeMapper;

    @PostMapping("/distname")
    public ResultVO getDistName(Integer distid){
        return new ResultVO<>(200, "查询成功！", distMapper.selectById(distid), null);
    }

    @PostMapping("/disttypelist")
    public ResultVO getDistTypeList(String dtname){
        QueryWrapper<DistType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dtname",dtname).in("state",200);
        return new ResultVO<>(200, "查询成功！", distTypeMapper.selectList(queryWrapper), null);
    }

    @PostMapping("/distlist")
    public ResultVO getDistList(Integer index, String dname, Integer dtid){
        IPage<Dist> distPage = new Page<>(index, 10);
        QueryWrapper<Dist> wrapper = new QueryWrapper<>();
        wrapper.in("dt_id", dtid).in("state",200);
        wrapper.and(i -> i.like("distname", dname).or().like("distnamezh",dname));
        return new ResultVO<>(200, "查询成功！", distMapper.selectPage(distPage, wrapper), null);
    }

    @PostMapping("/getDistList")
    public ResultVO getDistList2(String dname){
        if (dname != null && !dname.equals("")) {
            QueryWrapper<Dist> wrapper = new QueryWrapper<>();
            wrapper.and(i -> i.like("distname", dname).or().like("distnamezh",dname));
            return new ResultVO<>(200, "查询成功！", distMapper.selectList(wrapper), null);
        } else {
            return new ResultVO<>(200, "查询成功！", null, null);
        }
    }

    @PostMapping("/addDistType")
    public ResultVO insertDistType(@RequestBody DistType distType) {
        return new ResultVO(200,"添加成功!",distTypeMapper.insert(distType),null);
    }

    @PostMapping("/editDistType")
    public ResultVO updateDistType(@RequestBody DistType distType) {
        return new ResultVO(200,"修改成功!",distTypeMapper.updateById(distType),null);
    }

    @PostMapping("/delDistType")
    public ResultVO deleteDistType(Integer dtid) {
        DistType distType = new DistType();
        distType.setDtid(dtid);
        distType.setState(201);
        return new ResultVO(200,"删除成功!",distTypeMapper.updateById(distType),null);
    }

    @PostMapping("/addDist")
    public ResultVO insertDist(@RequestBody Dist dist) {
        return new ResultVO(200,"添加成功!",distMapper.insert(dist),null);
    }

    @PostMapping("/editDist")
    public ResultVO updateDist(@RequestBody Dist dist) {
        return new ResultVO(200,"修改成功!",distMapper.updateById(dist),null);
    }

    @PostMapping("/delDist")
    public ResultVO deleteDist(Integer distid) {
        Dist dist = new Dist();
        dist.setDistid(distid);
        dist.setState(201);
        System.out.println("Dist -> " + dist);
        return new ResultVO(200,"删除成功!",distMapper.updateById(dist),null);
    }
}
