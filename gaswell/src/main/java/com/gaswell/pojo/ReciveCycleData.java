package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("recivecycledata")
public class ReciveCycleData {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty(value = "井口温度")
    private Double jkwd;
    @ExcelProperty(value = "套管压力")
    private Double tgyl;
    @ExcelProperty(value = "油管压力")
    private Double ygyl;
    @ExcelProperty(value = "外输压力")
    private Double wsyl;
    @ExcelProperty(value = "产液流量")
    private Double cyell;
    @ExcelProperty(value = "产油流量")
    private Double cyoull;
    @ExcelProperty(value = "产水流量")
    private Double cshuill;
    @ExcelProperty(value = "产气流量")
    private Double cqill;
    @ExcelProperty(value = "加药压力")
    private Double jyyl;
    @ExcelProperty(value = "加药流量")
    private Double jyll;

    @ExcelProperty(value = "气井开关中间量")
    private Double qjkgzjl;
    @ExcelProperty(value = "泡排开关中间量")
    private Double ppkgzjl;
    @ExcelProperty(value = "注醇开关中间量")
    private Double zckgzjl;
    @ExcelProperty(value = "设备ID")
    private String deviceId;

    @ExcelProperty(value = "采集时间")
    private String datetime;

    @ExcelProperty(value = "RTU电压")
    private String rtuVoltage;

    @ExcelProperty(value = "RTU第1路AI输入值")
    private String rtuAi;
    @ExcelProperty(value = "气井名称")
    private String qjmc;
    @ExcelProperty(value = "英文编号")
    private String ywbh;
    @ExcelProperty(value = "中文编号")
    private String zwbh;
    @ExcelProperty(value = "生产区")
    private String scq;

    @ExcelProperty(value = "是否积液")
    private String sfjy_java;
    @ExcelProperty(value = "是否水合物")
    private String sfshw_java;
    @ExcelProperty(value = "是否间开")
    private String sfjk_java;
    @ExcelProperty(value="是否设备异常")
    private String sfsbyc_java;
    @ExcelProperty(value = "是否积液-py")
    private String sfjy_py;
    @ExcelProperty(value = "是否水合物-py")
    private String sfshw_py;
    @ExcelProperty(value = "是否间开-py")
    private String sfjk_py;

    @ExcelProperty(value = "泡排剂加注量")
    private String ppjjzl_java;
    @ExcelProperty(value = "甲醇加注量")
    private String jcjzl_java;
    @ExcelProperty(value = "泡排剂加注量-py")
    private String ppjjzl_py;
    @ExcelProperty(value = "甲醇加注量-py")
    private String jcjzl_py;


    @ExcelProperty(value = "是否异常")
    private String iserror;
    @ExcelProperty(value = "异常是否已处理")
    private String isfixed;
    @ExcelProperty(value = "备注")
    private String remark;
}
