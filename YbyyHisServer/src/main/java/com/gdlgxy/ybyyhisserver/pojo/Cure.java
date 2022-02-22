package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_cure")
public class Cure {
    @TableId(type = IdType.AUTO)
    private Integer cureid;
    private String cname;
    private String cnamezh;
    private Double price;
    private String loc;
    private Integer state;
}
