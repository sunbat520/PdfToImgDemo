package com.sunbat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CPR066
 * @since 2020-12-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrEtCommResult<T> {
    //唯一id，用于问题定位
    public String sid;
    //图像识别结果
    public T data;
    //图片旋转角度
    public int angle;
}
