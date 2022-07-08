package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_booking_logs")
public class BookingLogs {
    @TableId(type = IdType.AUTO)
    private Integer blid;
    private Integer registerId;
    private Integer patientId;
    private String pname;
    private Integer state;
    private Timestamp createTime;
}
