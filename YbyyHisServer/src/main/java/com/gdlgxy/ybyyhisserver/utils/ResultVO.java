package com.gdlgxy.ybyyhisserver.utils;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;
    private Integer count;

    public ResultVO(){}

    public ResultVO(Integer code, String msg, T data, Integer count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }
}
