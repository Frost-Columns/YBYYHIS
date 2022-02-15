package com.gdlgxy.ybyyhisserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class States {
    @TableId
    private Integer stateid;
    private Integer stateno;
    private String statename;
}
