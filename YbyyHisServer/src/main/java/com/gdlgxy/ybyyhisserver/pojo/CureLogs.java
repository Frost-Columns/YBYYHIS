package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("tbl_cure_logs")
public class CureLogs {
    @TableId(type = IdType.AUTO)
    private Integer clid;
    private Integer caseId;
    private Integer patientId;
    private String pname;
    private Integer cureId;
    private String loc;
    private String cname;
    private String cureResults;
    private String cricon;
    private Timestamp createTime;
    private Timestamp cureTime;
    private Timestamp checkResultTime;
    private Integer state;
    private String sname;
}
