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
 * @Date: 2022/01/16/ 22:04
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 控制指令记录
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "controlvalvelog")
public class ControlValveLog {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "设备ID")
    private String deviceId;
    @ExcelProperty(value = "开关量")
    private String plcPosition;
    @ExcelProperty(value = "状态")
    private String status;
    @ExcelProperty(value = "控制时间")
    private String controlTime;
    @ExcelProperty(value = "是否成功")
    private String result;
    @ExcelProperty(value = "指令内容")
    private String command;
}
