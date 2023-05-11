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
@TableName("algorithmrecords")
public class    AlgorithmRecords {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer record_id;
    @ExcelProperty(value = "用户id")
    private Integer user_id;
    @ExcelProperty(value = "算法类型")
    private String algorithm_type;
    @ExcelProperty(value = "算法名称")
    private String algorithm_name;
    @ExcelProperty(value = "算法参数")
    private String algorithm_parameter;
    @ExcelProperty(value = "数据表类型")
    private String database_type;
    @ExcelProperty(value = "区块")
    private String database_condition_type;
    @ExcelProperty(value = "对应区块/井号字符串")
    private String database_condition;
    @ExcelProperty(value = "训练集分数")
    private String trainGrade;
    @ExcelProperty(value = "测试集分数")
    private String testGrade;
    @ExcelProperty(value = "是否使用")
    private String ifUse;
}
