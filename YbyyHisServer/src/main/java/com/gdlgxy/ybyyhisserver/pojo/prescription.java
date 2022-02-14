package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_prescription")
public class prescription {
    @TableId
    private Integer presid;
    private Integer caseId;
    private Integer drugsId;
    private Integer count;
    private Double price;
    private String takeRemarks;
    private Integer state;
}
