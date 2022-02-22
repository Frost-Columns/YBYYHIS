package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_case")
public class Case {
    @TableId(type = IdType.AUTO)
    private Integer caseid;
    private Integer patientId;
    private String symptom;
    private String historyOfTreatment;
    private String pastHistory;
    private String allergen;
    private String checkResultStr;
    private Integer distId;
    private String distRemarks;
    private String cureSchedule;
    private String prescriptionStr;
    private Timestamp createTime;
}
