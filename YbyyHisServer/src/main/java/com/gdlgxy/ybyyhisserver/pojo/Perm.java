package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_perm")
public class Perm {
    @TableId(type = IdType.AUTO)
    private Integer permid;
    private String permname;
    private String permnamezh;
    private String iden;
    private Timestamp createTime;
}
