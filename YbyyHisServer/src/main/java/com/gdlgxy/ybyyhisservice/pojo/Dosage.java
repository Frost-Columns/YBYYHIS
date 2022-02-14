package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_dosage")
public class Dosage {
    @TableId
    private Integer dosageid;
    private String dosagename;
    private Integer state;
}
