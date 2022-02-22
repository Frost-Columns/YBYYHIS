package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dist_type")
public class DistType {
    @TableId(type = IdType.AUTO)
    private Integer dtid;
    private String dtname;
    private Integer state;
}
