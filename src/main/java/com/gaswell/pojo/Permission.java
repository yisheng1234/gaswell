package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer permission_id;
    @ExcelProperty(value = "权限名称")
    private String permission_name;
    @ExcelIgnore
    private String path;
    @ExcelIgnore
    private Integer father;
    @ExcelIgnore
    private Integer type;
    @ExcelIgnore
    private String icon;
}
