package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dist")
public class Dist {
    @TableId
    private Integer distid;
    private String distname;
    private String distnamezh;
    private Integer dtId;
    private String distResults;
    private Integer state;
}
