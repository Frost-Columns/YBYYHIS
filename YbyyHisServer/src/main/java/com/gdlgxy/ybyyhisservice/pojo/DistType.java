package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dist_type")
public class DistType {
    @TableId
    private Integer dtid;
    private String dtname;
    private Integer state;
}
