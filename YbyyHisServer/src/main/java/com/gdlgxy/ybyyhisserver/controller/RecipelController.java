package com.gdlgxy.ybyyhisserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdlgxy.ybyyhisserver.mapper.DrugsMapper;
import com.gdlgxy.ybyyhisserver.mapper.RecipelInfoMapper;
import com.gdlgxy.ybyyhisserver.mapper.RecipelMapper;
import com.gdlgxy.ybyyhisserver.pojo.Drugs;
import com.gdlgxy.ybyyhisserver.pojo.Recipel;
import com.gdlgxy.ybyyhisserver.pojo.RecipelInfo;
import com.gdlgxy.ybyyhisserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RecipelController {

    @Autowired
    private RecipelMapper recipelMapper;
    @Autowired
    private RecipelInfoMapper recipelInfoMapper;
    @Autowired
    private DrugsMapper drugsMapper;

    @PostMapping("/recipellist")
    public ResultVO getRecipelList(String pname) {
        Map<String,Object> map = new HashMap<>();
        map.put("recipellist800",recipelMapper.selectList(new QueryWrapper<Recipel>().like("patient_name", pname).in("state",800)));
        map.put("recipellist801",recipelMapper.selectList(new QueryWrapper<Recipel>().like("patient_name", pname).in("state",801)));
        map.put("recipellist802",recipelMapper.selectList(new QueryWrapper<Recipel>().like("patient_name", pname).in("state",802)));
        return new ResultVO<>(200, "查询成功！", map, null);
    }

    @PostMapping("/getRecipel")
    public ResultVO getRecipelList(Integer caseId) {
        List<Recipel> recipels = recipelMapper.selectList(new QueryWrapper<Recipel>().like("case_id", caseId));
        if (recipels.size() > 0) {
            return new ResultVO<>(200, "查询成功！", recipels.get(0), null);
        } else {
            return new ResultVO<>(200, "查询成功！", null, null);
        }
    }

    @PostMapping("/recipelky")
    public ResultVO updateRecipelKYState(Integer recipelid) {
        Recipel recipel = new Recipel();
        recipel.setRecipelid(recipelid);
        recipel.setState(801);
        return new ResultVO<>(200,"修改成功！",recipelMapper.updateById(recipel),null);
    }

    @PostMapping("/recipelqy")
    public ResultVO updateRecipelQYState(Integer recipelid) {
        Recipel recipel = new Recipel();
        recipel.setRecipelid(recipelid);
        recipel.setState(802);
        return new ResultVO<>(200,"修改成功！",recipelMapper.updateById(recipel),null);
    }

    @PostMapping("/recipelinfolist")
    public ResultVO getRecipelInfoList(Integer recipelId) {
        QueryWrapper<RecipelInfo> wrapper = new QueryWrapper<>();
        wrapper.in("recipel_id", recipelId);
        return new ResultVO<>(200, "查询成功！", recipelInfoMapper.selectList(wrapper), null);
    }


    @PostMapping("/addRecipelInfo")
    public ResultVO insertRecipelInfo(@RequestBody RecipelInfo recipelInfo) {
        Drugs drugs = drugsMapper.selectById(recipelInfo.getDrugsId());
        recipelInfo.setDname(drugs.getDrugsnamezh());
        recipelInfo.setUnitPrice(drugs.getPrice());
        recipelInfo.setPrice(recipelInfo.getCount() * drugs.getPrice());
        drugs.setCount(drugs.getCount()-recipelInfo.getCount());
        drugsMapper.updateById(drugs);
        return new ResultVO(200,"添加成功!",recipelInfoMapper.insert(recipelInfo),null);
    }

    @PostMapping("/addRecipel")
    public ResultVO addRecipel(Integer caseid, String patientName) {
        Recipel recipel = new Recipel();
        recipel.setCaseId(caseid);
        recipel.setPatientName(patientName);
        recipelMapper.insert(recipel);
        return new ResultVO(200,"添加成功!",recipelMapper.selectById(recipel.getRecipelid()),null);
    }

    @PostMapping("/editRecipelInfo")
    public ResultVO updateRecipelInfo(@RequestBody RecipelInfo recipelInfo) {
        Drugs drugs = drugsMapper.selectById(recipelInfo.getDrugsId());
        recipelInfo.setDname(drugs.getDrugsnamezh());
        recipelInfo.setPrice(recipelInfo.getCount() * drugs.getPrice());
        drugs.setCount(drugs.getCount()+recipelInfoMapper.selectById(recipelInfo.getRecipelId()).getCount()-recipelInfo.getCount());
        drugsMapper.updateById(drugs);
        return new ResultVO(200,"添加成功!",recipelInfoMapper.updateById(recipelInfo),null);
    }

    @PostMapping("/delRecipelInfo")
    public ResultVO deleteRecipelInfo(Integer riid) {
        return new ResultVO(200,"删除成功!",recipelInfoMapper.deleteById(riid),null);
    }

    @PostMapping("/getRecipelPrice")
    public ResultVO updateRecipelPrice(Integer recipelid) {
        List<RecipelInfo> list = recipelInfoMapper.selectList(new QueryWrapper<RecipelInfo>().in("recipel_id", recipelid));
        Double price = 0D;
        for (RecipelInfo r : list) {
            price += r.getPrice();
        }
        Recipel recipel = new Recipel();
        recipel.setRecipelid(recipelid);
        recipel.setPrice(price);
        recipelMapper.updateById(recipel);
        return new ResultVO(200,"获取成功!",recipelMapper.selectById(recipelid),null);
    }
}
