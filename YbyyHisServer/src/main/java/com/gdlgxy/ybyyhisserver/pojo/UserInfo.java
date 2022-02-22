package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_user_info")
public class UserInfo {
    @TableId(type = IdType.AUTO)
    private Integer userid;
    private String username;
    private String usernamezh;
    private String userpwd;
    private String salt;
    private Integer sex;
    private Timestamp birthday;
    private Integer roleId;
    private Integer dtId;
    private Integer deptId;
    private Timestamp createTime;
    private Integer state;
    private String sname;
    private String uprofile;
}
