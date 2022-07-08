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
    private Integer rgId;
    private Integer userId;
    private String username;
    private Integer state;
    private Integer count;
    private Integer rlId;
    private Boolean mondayAm = false; //周一
    private Boolean mondayPm = false;
    private Boolean tuesdayAm = false; //周二
    private Boolean tuesdayPm = false;
    private Boolean wednesdayAm = false; //周三
    private Boolean wednesdayPm = false;
    private Boolean thursdayAm = false; //周四
    private Boolean thursdayPm = false;
    private Boolean fridayAm = false; //周五
    private Boolean fridayPm = false;
    private Boolean saturdayAm = false; //周六
    private Boolean saturdayPm = false;
    private Boolean sundayAm = false; //周日
    private Boolean sundayPm = false;
}
