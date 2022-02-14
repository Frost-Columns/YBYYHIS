package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_cure")
public class Cure {
    @TableId
    private Integer cureid;
    private String cname;
    private String cnamezh;
    private Double price;
    private String loc;
    private Integer state;
}
