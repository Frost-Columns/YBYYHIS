package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_treatment_logs")
public class TreatmentLogs {
    @TableId(type = IdType.AUTO)
    private Integer tlid;
    private Integer registerId;
    private Integer caseId;
    private String pname;
    private Timestamp updateTime;
    private Integer state;
}
