package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_drugs")
public class Drugs {
    @TableId
    private Integer drugsid;
    private String drugsname;
    private String drugsnamezh;
    private Integer dtId;
    private Integer count;
    private Integer dosageId;
    private Double price;
    private String drugeRemarks;
    private Integer state;
}
