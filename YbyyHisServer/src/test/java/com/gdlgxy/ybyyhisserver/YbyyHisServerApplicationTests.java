package com.gdlgxy.ybyyhisserver;

import com.gdlgxy.ybyyhisserver.mapper.*;
import com.gdlgxy.ybyyhisserver.pojo.Dist;
import com.gdlgxy.ybyyhisserver.pojo.DistType;
import com.gdlgxy.ybyyhisserver.pojo.Dosage;
import com.gdlgxy.ybyyhisserver.pojo.Drugs;
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
    private DrugsMapper drugsMapper;

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
            String sql = "SELECT * FROM dms_drug";
            ResultSet rs = stmt.executeQuery(sql);
            List<Drugs> list = new ArrayList<>();
            while(rs.next()){
                Drugs drugs = new Drugs();
                drugs.setDrugsname(rs.getString("mnemonic_code"));
                drugs.setDrugsnamezh(rs.getString("name"));
                drugs.setFormat(rs.getString("format"));
                drugs.setPrice(rs.getDouble("price"));
                drugs.setDosageId(rs.getInt("dosage_id"));
                drugs.setManufacturer(rs.getString("manufacturer"));
                drugs.setDrugeRemarks(rs.getString("generic_name"));
                list.add(drugs);
            }
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("写入开始"+list.size());
            for(Drugs d:list){
                drugsMapper.insert(d);
            }
            System.out.println("写入完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
