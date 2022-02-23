package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_role_perm")
public class RolePerm {
    @TableId(type = IdType.AUTO)
    private Integer rpid;
    private Integer roleId;
    private Integer permId;

    public RolePerm(){}

    public RolePerm(Integer roleid, Integer permid){
        this.roleId = roleid;
        this.permId = permid;
    }
}
