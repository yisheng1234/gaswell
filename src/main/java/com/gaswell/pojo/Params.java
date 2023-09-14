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
@TableName(value = "params")
public class Params {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer param_id;
    @ExcelProperty(value = "参数名")
    private String param_name;
    @ExcelProperty(value = "某井号的参数")
    private String param_jh;
    @ExcelProperty(value = "参数值")
    private float param_value;
    @ExcelProperty(value = "参数单位")
    private String param_dw;
    @ExcelProperty(value = "参数备注")
    private String param_bz;
}
