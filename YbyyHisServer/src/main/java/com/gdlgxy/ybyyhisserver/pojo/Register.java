package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName
public class Register {
    @TableId
    private Integer registerid;
    private Integer deptId;
    private Timestamp createTime;
    private Integer meridiem;
    private Integer userId;
    private Integer surcount;
    private Integer state;
    private Integer rlId;
}
