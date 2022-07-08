package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_register_group")
public class RegisterGroup {
    @TableId(type = IdType.AUTO)
    private Integer rgid;
    private Integer dtId;
    private String rgname;
    private String remarks;
    private Timestamp createTime;
}
