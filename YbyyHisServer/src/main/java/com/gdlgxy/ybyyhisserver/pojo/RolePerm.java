package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_role_perm")
public class RolePerm {
    @TableId
    private Integer rpid;
    private Integer roleId;
    private Integer permId;
}