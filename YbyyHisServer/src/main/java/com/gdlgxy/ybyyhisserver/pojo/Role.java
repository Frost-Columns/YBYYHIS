package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_role")
public class Role {
    @TableId
    private Integer roleid;
    private String rolename;
    private String rolenamezh;
    private Integer state;
}
