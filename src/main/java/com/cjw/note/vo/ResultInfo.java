package com.cjw.note.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装返回结果
 */
@Getter
@Setter
public class ResultInfo <T>{
    private Integer code; //状态码 成功=1 ，失败=0
    private String  msg;//提示信息
    private T result;//返回对象
}
