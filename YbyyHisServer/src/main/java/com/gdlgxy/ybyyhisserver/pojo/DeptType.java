package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dept_type")
public class DeptType {
    @TableId(type = IdType.AUTO)
    private Integer dtid;
    private String dtname;
    private Integer state;
}
