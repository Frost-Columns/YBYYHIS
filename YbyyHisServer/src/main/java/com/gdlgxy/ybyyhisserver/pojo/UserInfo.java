package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName
public class UserInfo {
    @TableId
    private Integer userid;
    private String username;
    private String usernamezh;
    private String userpwd;
    private String salt;
    private Integer sex;
    private Timestamp birthday;
    private Integer roleId;
    private Integer deptId;
    private Timestamp createTime;
    private Integer state;
    private String uprofile;
}