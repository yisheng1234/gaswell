package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:50
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "param")
public class Param {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer param_id;
    @ExcelProperty(value = "参数名")
    private String param_name;
    @ExcelProperty(value = "参数值")
    private Double param_value;
    @ExcelProperty(value = "参数单位")
    private String param_unit;
}
