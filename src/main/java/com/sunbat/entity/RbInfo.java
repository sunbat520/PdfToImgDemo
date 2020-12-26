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
public class RbInfo {
    //保险单号
    public String policyNo;
    //被保险人
    public String insuredName;
    //被保险人身份证号码(统一社会信用代码)
    public String insuredCardNo;
    //地址
    public String address;
    //号牌号码
    public String plate;
    //发动机号码
    public String engineNo;
    //识别代码(车架号)
    public String VIN;
    //厂牌型号
    public String brandModel;
    //登记日期
    public String registerDate;
    //保险费合计(人民币大写)
    public String insuranceTotal;
    //保险期时效
    public String insuranceDataLimit;
    //死亡伤残赔偿限额
    public String ddcLimit;
    //无责任死亡伤残赔偿限额
    public String nlddcLimit;
    //医疗费用赔偿限额
    public String mecLimit;
    //无责任医疗费用赔偿限额
    public String nlmecLimit;
    //财产损失赔偿限额
    public String pdcLimit;
    //无责任财产损失偿限额
    public String nlpdcLimit;

}
