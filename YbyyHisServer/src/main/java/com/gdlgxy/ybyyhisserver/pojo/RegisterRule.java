package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_register_rule")
public class RegisterRule {
    @TableId(type = IdType.AUTO)
    private Integer rrid;
    private Integer deptId;
    private Integer rrweek;
    private Integer meridiem;
    private Integer userId;
    private Integer state;
    private Integer count;
    private Integer rlId;
}
