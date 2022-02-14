package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_vos_gains")
public class VosGains {
    @TableId
    private Integer vgid;
    private Integer caseId;
    private Double price;
    private String item;
    private Timestamp createTime;
}
