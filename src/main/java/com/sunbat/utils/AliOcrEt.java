package com.sunbat.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunbat.common.OcrEtBody;
import com.sunbat.entity.OcrEtCommResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CPR066
 * @since 2020-12-22
 */
public class AliOcrEt {

    private static String host = "https://ocrapi-et.taobao.com";
    private static String path = "/ocrservice/et";
    private static  String method = "POST";
    private static String appcode = "9f2e8ad42e4d455dac90994818514e7b";
    private static String templateId = "2c47da3442eb2cf4efb39ee98aa969255e6ebce71a6f1400fdb33fd2549d8639889fa739";


    public static String getOcrKVEtInfo(String destPath) throws Exception {

        Map <String, String> headers = new HashMap <String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map <String, String> querys = new HashMap <String, String>();

        String imgBase64 = getBase64FromImgToAli(destPath);

        OcrEtBody ocrEtBody = new OcrEtBody();
        ocrEtBody.setImg(imgBase64);
        ocrEtBody.setTemplateId(templateId);

        String bodys = JSONObject.toJSONString(ocrEtBody);

        HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        return EntityUtils.toString(response.getEntity());

    }

    public static String getBase64FromImgToAli(String destPath){
        InputStream is = null;
        byte[] data = null;
        try{
            is = new FileInputStream(destPath);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return DatatypeConverter.printBase64Binary(data);
    }

    public static void main(String[] args) throws Exception {

//        // 这样写的好处在于、足够灵活、可以添加任意监听器来检测扫描过程，可以动态添加过滤规则。
//        List <File> list = FileFinder.getInstance("D:\\temp\\pdf")
//                .registerFileFountListener(f -> System.out.println("[监听器]监听到新文件:"+f.getName()+",他的父级目录是:"+f.getParent()))
//                .addFileIgnore(f -> f.getName().endsWith(".jpg"))               // 滤除所有.jpg文件
//                .addFileIgnore(f -> f.isDirectory() && f.getName().equals("1")) // 滤除1文件夹下的所有文件
//                .doFind();
//
//        System.out.println("\n文件扫描完成,下面打印\n");
//        for (File file : list) {
//            System.out.println(file.getName()+" : "+ file.getPath());
////            String fileName = file.getName().replace(".pdf","");
//            String returnData = getOcrKVEtInfo(file.getPath());
//            OcrEtCommResult result = JSON.parseObject(returnData, OcrEtCommResult.class);
//        }
        String returnData = getOcrKVEtInfo("D:\\temp\\pdf\\柴海连交强0.jpg");
        OcrEtCommResult result = JSON.parseObject(returnData, OcrEtCommResult.class);
        System.out.println(result.getSid());
        System.out.println(result.getAngle());
        Object data = result.getData();
        System.out.println("end");
    }
}
