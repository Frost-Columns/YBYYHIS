package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_cure_logs")
public class CureLogs {
    @TableId(type = IdType.AUTO)
    private Integer clid;
    private Integer patientId;
    private Integer cureId;
    private Integer deptId;
    private String cureResults;
    private Byte[] cricon;
    private Timestamp createTime;
    private Timestamp cureTime;
    private Timestamp checkResultTime;
}
