package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_role_menu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private Integer dmid;
    private Integer roleId;
    private Integer menuId;

    public RoleMenu(){}

    public RoleMenu(Integer roleid, Integer menuid){
        this.roleId = roleid;
        this.menuId = menuid;
    }
}
