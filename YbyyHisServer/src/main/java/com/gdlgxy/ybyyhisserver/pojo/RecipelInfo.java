package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_recipel_info")
public class RecipelInfo {
    @TableId(type = IdType.AUTO)
    private Integer riid;
    private Integer recipelId;
    private Integer drugsId;
    private String dname;
    private Integer count;
    private Double unitPrice;
    private Double price;
    private String takeRemarks;
}
