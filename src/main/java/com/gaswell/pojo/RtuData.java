package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rtu_data_qj")
public class RtuData {

    @ExcelProperty(value = "井号")
    private String jh;

    @ExcelProperty(value = "采集时间")
    private String cjsj;
    @ExcelProperty(value = "油管压力")
    @TableField("J_YGYL")
    private double YGYL;
    @TableField("J_TGYL")
    @ExcelProperty(value = "套管压力")
    private float TGYL;
    @TableField("J_ZQYL")
    @ExcelProperty(value = "注气压力")
    private float ZQYL;
    @TableField("J_YGWD")
    @ExcelProperty(value = "油管温度")
    private float YGWD;
    @TableField("J_WSWD")
    @ExcelProperty(value = "外输温度")
    private float WSWD;
    @TableField("J_FCS")
    @ExcelProperty(value = "安全阀开")
    private String FCS;
    @TableField("J_FOS")
    @ExcelProperty(value = "安全阀关")
    private String FOS;
    @TableField("J_VVC")
    @ExcelProperty(value = "安全阀停止")
    private String VVC;
    @TableField("Z_JZWD")
    @ExcelProperty(value = "进站温度")
    private float JZWD;
    @TableField("Z_SJHWD")
    @ExcelProperty(value = "三节后温度")
    private float SJHWD;
    @TableField("Z_JRHWD")
    @ExcelProperty(value = "加热后温度")
    private float JRHWD;
    @TableField("Z_JZYL")
    @ExcelProperty(value = "进站压力")
    private float JZYL;
    @TableField("Z_SJHYL")
    @ExcelProperty(value = "三节后压力")
    private float SJHYL;

}
