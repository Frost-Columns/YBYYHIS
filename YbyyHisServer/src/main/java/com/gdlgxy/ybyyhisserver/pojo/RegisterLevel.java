package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_register_level")
public class RegisterLevel {
    @TableId(type = IdType.AUTO)
    private Integer rlid;
    private String rlname;
}
