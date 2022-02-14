package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_role_menu")
public class RoleMenu {
    @TableId
    private Integer dmid;
    private Integer roleId;
    private Integer menuId;
}
