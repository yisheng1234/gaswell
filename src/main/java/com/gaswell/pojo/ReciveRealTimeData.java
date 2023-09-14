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
@TableName("reciverealtimedata")
public class ReciveRealTimeData {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
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
//    @ExcelProperty(value = "注醇量")
//    private Double zcl;
    @ExcelProperty(value = "加药压力")
    private Double jyyl;
    @ExcelProperty(value = "加药流量")
    private Double jyll;

    @ExcelProperty(value = "气井开关中间量")
    private Double qjkgzjl;
    @ExcelProperty(value = "炮排中间开关量")
    private Double ppkgzjl;
    @ExcelProperty(value = "注醇开关中间量")
    private Double zckgzjl;
    @ExcelProperty(value = "设备ID")
    private String deviceId;

}
