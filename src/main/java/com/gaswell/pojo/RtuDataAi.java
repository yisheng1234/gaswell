package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rtu_data_qj_ai")
public class RtuDataAi {

    @ExcelProperty(value = "井号")
    private String jh;

    @ExcelProperty(value = "采集时间")
    private String cjsj;

    @ExcelProperty(value = "油管压力")
    private float J_YGYL;

    @ExcelProperty(value = "套管压力")
    private float J_TGYL;

    @ExcelProperty(value = "注气压力")
    private float J_ZQYL;

    @ExcelProperty(value = "油管温度")
    private float J_YGWD;

    @ExcelProperty(value = "外输温度")
    private float J_WSWD;

    @ExcelProperty(value = "安全阀开")
    private String J_FCS;

    @ExcelProperty(value = "安全阀关")
    private String J_FOS;

    @ExcelProperty(value = "安全阀停止")
    private String J_VVC;

    @ExcelProperty(value = "进站温度")
    private float Z_JZWD;

    @ExcelProperty(value = "三节后温度")
    private float Z_SJHWD;

    @ExcelProperty(value = "加热后温度")
    private float Z_JRHWD;

    @ExcelProperty(value = "进站压力")
    private float Z_JZYL;

    @ExcelProperty(value = "三节后压力")
    private float Z_SJHYL;


    // 基于机理公式判断
    @ExcelProperty(value = "是否积液——机理——1=是；2=否")
    private int sfjy_jl;
    @ExcelProperty(value = "是否冻堵——机理——1=是；2=否")
    private int sfdd_jl;
    @ExcelProperty(value = "间开————机理——1=开；2=关")
    private int jk_jl;
    @ExcelProperty(value="泡排剂加注量——机理")
    private int ppjjzl_jl;
    @ExcelProperty(value="甲醇加注量——机理")
    private int jcjzl_jl;

    // 基于大数据分析
    @ExcelProperty(value = "是否积液——AI——1=是；2=否")
    private int sfjy_py;
    @ExcelProperty(value = "是否冻堵——AI——1=是；2=否")
    private int sfdd_py;
    @ExcelProperty(value = "间开————AI——1=开；2=关")
    private int jk_py;

    @ExcelProperty(value = "卸载流量——机理——用于预警")
    private float xzll_jl;
    @ExcelProperty(value = "备注，如果没有诊断结果，这里显示原因")
    private String bz;



}
