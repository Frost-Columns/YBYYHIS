package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("tbl_booking_logs")
public class BookingLogs {
    @TableId
    private Integer blid;
    private Integer registerId;
    private Integer patientId;
    private Integer state;
    private Timestamp createTime;
}
