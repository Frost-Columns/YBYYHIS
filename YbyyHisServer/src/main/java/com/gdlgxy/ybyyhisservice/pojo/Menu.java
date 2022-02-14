package com.gdlgxy.ybyyhisservice.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_menu")
public class Menu {
    @TableId
    private Integer menuid;
    private Integer parent;
    private String menuname;
    private String menunamezh;
    private String url;
}
