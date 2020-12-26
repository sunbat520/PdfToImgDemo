package com.sunbat.common;

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
public class OcrEtBody {

    //图像数据：base64编码，要求base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和url参数只能同时存在一个
    private String img;
    //图像url地址：图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和img参数只能同时存在一个
    private String url;
    //模版Id，请参考ocr.aliyun.com/et/templates.htm信息
    private String templateId;
}
