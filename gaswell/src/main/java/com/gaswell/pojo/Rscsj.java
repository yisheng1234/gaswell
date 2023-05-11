package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "rscsj", autoResultMap = true)
public class Rscsj {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(value = "r_id", type = IdType.AUTO)
    private Integer r_id;
    @TableField("r_xh")
    @ExcelProperty(value = "序号")
    private String r_xh;
    @ExcelProperty(value = "气田")
    private String r_qt;
    @ExcelProperty(value = "区名")
    private String r_qm;
    @ExcelProperty(value = "站名")
    private String r_zm;
    @ExcelProperty(value = "日期")
    private String r_rq;
    @ExcelProperty(value = "生产时间")
    private String r_scsj;
    @ExcelProperty(value = "井口温度")
    private String r_jkwd;
    @ExcelProperty(value = "气嘴直径")
    private String r_qzzj;
    @ExcelProperty(value = "最高油压")
    private String r_zgyy;
    @ExcelProperty(value = "最低油压")
    private String r_zdyy;
    @ExcelProperty(value = "平均油压")
    private String r_pjyy;
    @ExcelProperty(value = "关井油压")
    private String r_gjyy;
    @ExcelProperty(value = "最高套压")
    private String r_zgty;
    @ExcelProperty(value = "最低套压")
    private String r_zdty;
    @ExcelProperty(value = "平均套压")
    private String r_pjty;
    @ExcelProperty(value = "关井套压")
    private String r_gjty;
    @ExcelProperty(value = "外输压力")
    private String r_wsyl;
    @ExcelProperty(value = "测气-挡板")
    private String r_cq_db;
    @ExcelProperty(value = "测气-温度")
    private String r_cq_wd;
    @ExcelProperty(value = "测气-压力")
    private String r_cq_yl;
    @ExcelProperty(value = "测气-压差")
    private String r_ce_yc;
    @ExcelProperty(value = "测气-日产气量")
    private String r_ce_rcql;
    @ExcelProperty(value = "产液-产油")
    private String r_cy_cy;
    @ExcelProperty(value = "产液-产水")
    private String r_ce_cs;
    @ExcelProperty(value = "加药-液体")
    private String r_jy_yt;
    @ExcelProperty(value = "加液-固体")
    private String r_jy_gt;
    @ExcelProperty(value = "自用气量")
    private String r_zyql;
    @ExcelProperty(value = "日注醇")
    private String r_rzc;
    @ExcelProperty(value = "关井原因")
    private String r_gjyyin;
    @ExcelProperty(value = "措施类别")
    private String r_cuoslb;
    @ExcelProperty(value = "测试类别")
    private String r_ceslb;
    @ExcelProperty(value = "备注")
    private String r_bz;
}
