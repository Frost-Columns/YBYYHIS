package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_case_dist")
public class CaseDist {
    @TableId(type = IdType.AUTO)
    private String cdid;
    private Integer caseId;
    private Integer distId;
    private String dname;
    private String remarks;
}
