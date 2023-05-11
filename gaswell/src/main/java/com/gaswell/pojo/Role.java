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
public class Role {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer role_id;
    @ExcelProperty(value = "角色种类")
    private String role_type;
    @ExcelProperty(value = "角色名")
    private String role_name;
    // private List<Permission> user_power;
    @ExcelProperty(value = "备注")
    private String role_remarks;
}
