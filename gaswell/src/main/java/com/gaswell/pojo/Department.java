package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author Lei Wang
 * @Date: 2021/12/09/ 0:21
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer department_id;
    @ExcelProperty(value = "部门种类")
    private Integer department_type;
    @ExcelProperty(value = "部门名")
    private String department_name;
    @ExcelProperty(value = "备注")
    private String department_remarks;
}
