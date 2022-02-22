package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class States {
    @TableId(type = IdType.AUTO)
    private Integer stateid;
    private Integer stateno;
    private String statename;
}
