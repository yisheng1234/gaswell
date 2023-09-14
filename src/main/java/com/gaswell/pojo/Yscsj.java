package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Yscsj {
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private Integer y_id;
    @ExcelProperty(value = "序号")
    private String y_xh;
    @ExcelProperty(value = "气田")
    private String y_qt;
    @ExcelProperty(value = "区名")
    private String y_qm;
    @ExcelProperty(value = "站名")
    private String y_zm;
    @ExcelProperty(value = "年月")
    private String y_ny;
    @ExcelProperty(value = "生产天数")
    private String y_scts;
    @ExcelProperty(value = "气嘴直径")
    private String y_qzzj;
    @ExcelProperty(value = "测气挡板")
    private String y_cqdb;
    @ExcelProperty(value = "油压")
    private String y_yy;
    @ExcelProperty(value = "套压")
    private String y_ty;
    @ExcelProperty(value = "井口温度")
    private String y_jkwd;
    @ExcelProperty(value = "外输压力")
    private String y_wsyl;
    @ExcelProperty(value = "日产气")
    private String y_rcq;
    @ExcelProperty(value = "日产水")
    private String y_rcs;
    @ExcelProperty(value = "日产油")
    private String y_rcy;
    @ExcelProperty(value = "月产气")
    private String y_ycq;
    @ExcelProperty(value = "月产水")
    private String y_ycs;
    @ExcelProperty(value = "月产油")
    private String y_ycy;
    @ExcelProperty(value = "年产气")
    private String y_ncq;
    @ExcelProperty(value = "年产水")
    private String y_ncs;
    @ExcelProperty(value = "年产油")
    private String y_ncy;
    @ExcelProperty(value = "累计产气")
    private String y_ljcq;
    @ExcelProperty(value = "累计产水")
    private String y_ljcs;
    @ExcelProperty(value = "累计产油")
    private String y_ljcy;
    @ExcelProperty(value = "月加药—液体")
    private String y_yjy_yt;
    @ExcelProperty(value = "月加药—固体")
    private String y_yjy_gt;
    @ExcelProperty(value = "月注醇")
    private String y_yzc;
    @ExcelProperty(value = "备注")
    private String y_bz;

}
