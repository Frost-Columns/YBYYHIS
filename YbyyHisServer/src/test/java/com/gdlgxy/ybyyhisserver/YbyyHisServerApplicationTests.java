package com.gdlgxy.ybyyhisserver;

import com.gdlgxy.ybyyhisserver.mapper.CureMapper;
import com.gdlgxy.ybyyhisserver.mapper.DistMapper;
import com.gdlgxy.ybyyhisserver.mapper.DistTypeMapper;
import com.gdlgxy.ybyyhisserver.mapper.StatesMapper;
import com.gdlgxy.ybyyhisserver.pojo.Dist;
import com.gdlgxy.ybyyhisserver.pojo.DistType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@MapperScan
class YbyyHisServerApplicationTests {

    @Autowired
    private DistMapper distMapper;

    @Test
    void contextLoads() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String jdbc = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
            String user = "root";
            String password = "123456";
            Class.forName(jdbc);
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM dms_dise";
            ResultSet rs = stmt.executeQuery(sql);
            List<Dist> list = new ArrayList<>();
            while(rs.next()){
                Dist dist = new Dist();
                dist.setDistname(rs.getString("code"));
                dist.setDistnamezh(rs.getString("name"));
                dist.setDtId(rs.getInt("cat_id"));
                list.add(dist);
            }
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("写入开始"+list.size());
            for(Dist d:list){
                distMapper.insert(d);
            }
            System.out.println("写入完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
