package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user", autoResultMap = true)
@Component
public class User {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer user_id;
    @ExcelProperty(value = "用户名")
    private String user_name;
    @ExcelProperty(value = "用户姓名")
    private String user_realname;
    @ExcelProperty(value = "用户密码")
    private String user_password;
    @ExcelProperty(value = "用户身份")
    private Integer user_role_id;
    @ExcelProperty(value = "用户部门")
    private Integer user_department_id;
    @ExcelProperty(value = "备注")
    private String user_remarks;
}
