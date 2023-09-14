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
@TableName("qj")
public class Qj {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "气井名称")
    private String qjmc;
    @ExcelProperty(value = "英文编号")
    private String ywbh;
    @ExcelProperty(value = "中文编号")
    private String zwbh;
    @ExcelProperty(value = "生产区")
    private String scq;
    @ExcelProperty(value = "通信编号")
    private String txbh;
    @ExcelProperty(value = "经纬度")
    private String jwd;
    @ExcelProperty(value = "监控标志")
    private String jkbz;
    @ExcelProperty(value = "间歇井")
    private String jxj;
    @ExcelProperty(value = "联系人姓名")
    private String lxrxm;
    @ExcelProperty(value = "联系人电话")
    private String lxrdh;
    @ExcelProperty(value = "是否数字化建设")
    private String sfszhjs;
    @ExcelProperty(value = "域名")
    private String ym;
    @ExcelProperty(value = "站名")
    private String zm;
    public Qj(String ywbh){
        this.ywbh=ywbh;
    }
}
