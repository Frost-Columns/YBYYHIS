package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_patient")
public class Patient {
    @TableId(type = IdType.AUTO)
    private Integer ptientid;
    private String pname;
    private Integer sex;
    private Timestamp birthday;
    private String idcard;
    private String home;
    private Integer phone;
    private Integer emergencyContact;
    private Integer caseId;
}
