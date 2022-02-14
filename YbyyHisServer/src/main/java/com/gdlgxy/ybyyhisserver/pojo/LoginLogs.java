package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_login_logs")
public class LoginLogs {
    @TableId
    private Integer llid;
    private String llname;
    private String llip;
    private Timestamp createTime;
    private Integer state;
}
