package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_recipel")
public class Recipel {
    @TableId(type = IdType.AUTO)
    private Integer recipelid;
    private Integer caseId;
    private String patientName;
    private Timestamp createTime;
    private Timestamp receiveTime;
    private Integer state;
    private Double price;
}
