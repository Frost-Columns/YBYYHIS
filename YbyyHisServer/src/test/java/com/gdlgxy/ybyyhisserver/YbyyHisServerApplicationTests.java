package com.gdlgxy.ybyyhisserver;

import com.gdlgxy.ybyyhisserver.mapper.CureMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan
class YbyyHisServerApplicationTests {

    @Autowired
    private StatesMapper statesMapper;

    @Autowired
    private CureMapper cureMapper;

    @Test
    void contextLoads() {
        System.out.println(statesMapper.selectList(null));
        System.out.println(cureMapper.selectList(null));
    }

}
