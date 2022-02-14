package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dept")
public class Dept {
    @TableId
    private Integer deptid;
    private String dname;
    private Integer dtId;
    private String deptRemarks;
    private String loc;
    private Integer state;
}
