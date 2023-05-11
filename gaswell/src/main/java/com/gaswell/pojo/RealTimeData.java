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
@TableName("realtimedata")
public class  RealTimeData {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty(value = "井口油压")
    private Double jkyy;
    @ExcelProperty(value = "井口温度")
    private Double jkwd;
    @ExcelProperty(value = "井口套压")
    private Double jkty;
    @ExcelProperty(value = "节流压力")
    private Double jlyl;
    @ExcelProperty(value = "节流温度")
    private Double jlwd;
    @ExcelProperty(value = "进站压力")
    private Double jzyl;
    @ExcelProperty(value = "进站温度")
    private Double jzwd;
    @ExcelProperty(value = "生产汇管压力")
    private Double schgyl;
    @ExcelProperty(value = "计量汇管压力")
    private Double jlhgyl;
    @ExcelProperty(value = "出口压力")
    private Double ckyl;

    @ExcelProperty(value = "出口温度")
    private Double ckwd;
    @ExcelProperty(value = "计量液位")
    private Double yw_jl;
    @ExcelProperty(value = "气量/液量")
    private Double qlyl;
    @ExcelProperty(value = "计量液位开关")
    private Double ywkg_jl;
    @ExcelProperty(value = "生产液位")
    private Double yw_sc;
    @ExcelProperty(value = "生产液位开关")
    private Double ywkg_sc;
    @ExcelProperty(value = "出站压力")
    private Double czyl;
    @ExcelProperty(value = "出站流量")
    private Double czll;




    @ExcelProperty(value = "设备ID")
    private String device_id;

    @ExcelProperty(value = "采集时间")
    private String datetime;

//    @ExcelProperty(value = "RTU电压")
//    private String rtuVoltage;
//
//    @ExcelProperty(value = "RTU第1路AI输入值")
//    private String rtuAi;

    // 气井基本信息
    @ExcelProperty(value = "气井名称")
    private String qjmc;
    @ExcelProperty(value = "英文编号")
    private String ywbh;
    @ExcelProperty(value = "中文编号")
    private String zwbh;
    @ExcelProperty(value = "生产区")
    private String scq;

    @ExcelProperty(value = "气田")
    private String qt;
    @ExcelProperty(value = "区名")
    private String qm;
    @ExcelProperty(value = "站名")
    private String zm;
    @ExcelProperty(value = "区块")
    private String qk;

    // 基于机理公式判断
    @ExcelProperty(value = "是否积液-基于机理公式判断")
    private String sfjy_java;
    @ExcelProperty(value = "是否水合物-基于机理公式判断")
    private String sfshw_java;
    @ExcelProperty(value = "是否间开-基于机理公式判断")
    private String sfjk_java;
    @ExcelProperty(value="是否设备异常-基于机理公式判断")
    private String sfsbyc_java;
    @ExcelProperty(value="泡排剂加注量-基于机理公式判断")
    private String ppjjzl_java;
    @ExcelProperty(value="甲醇加注量-基于机理公式判断")
    private String jcjzl_java;

    // 基于大数据分析
    @ExcelProperty(value = "是否积液-基于大数据分析")
    private String sfjy_py;
    @ExcelProperty(value = "是否水合物-基于大数据分析")
    private String sfshw_py;
    @ExcelProperty(value = "是否间开-基于大数据分析")
    private String sfjk_py;
    @ExcelProperty(value = "泡排剂加注量-基于大数据分析")
    private String ppjjzl_py;
    @ExcelProperty(value = "甲醇加注量-基于大数据分析")
    private String jcjzl_py;


    // 机理公式计算中间值
    @ExcelProperty(value = "水合物临界温度")
    private String shwljwd;
    @ExcelProperty(value = "卸载流速")
    private String xzls;
    @ExcelProperty(value = "卸载流量")
    private String xzll;
    @ExcelProperty(value = "气体偏差系数")
    private String qtpcxs;


    @ExcelProperty(value = "是否设备数据异常")
    private String iserror;
    @ExcelProperty(value = "数据异常是否已处理")
    private String isfixed;
    @ExcelProperty(value = "备注")
    private String remark;
}
