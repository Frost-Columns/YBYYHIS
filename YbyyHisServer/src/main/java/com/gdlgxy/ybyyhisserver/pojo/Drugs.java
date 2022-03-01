package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_drugs")
public class Drugs {
    @TableId(type = IdType.AUTO)
    private Integer drugsid;
    private String drugsname;
    private String drugsnamezh;
    private Integer dtId;
    private Integer count;
    private String format;
    private Integer dosageId;
    private String manufacturer;
    private Double price;
    private String drugeRemarks;
    private Integer state;
}
