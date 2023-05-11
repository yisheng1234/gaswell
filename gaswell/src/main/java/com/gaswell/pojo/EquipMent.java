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
@TableName("equipment")
public class EquipMent {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "设备id")
    private String eq_id;
    @ExcelProperty(value = "设备ip")
    private String eq_ip;
    @ExcelProperty(value = "端口")
    private Integer eq_port;
    @ExcelProperty(value = "措施井井号")
    private String jh;
    @ExcelProperty(value = "是否上线")
    private Integer status;
    @ExcelProperty(value = "设备上线时间")
    private String time;
    @ExcelProperty(value = "x坐标")
    private float pos_x;
    @ExcelProperty(value = "y坐标")
    private float pos_y;
    @ExcelProperty(value = "备注")
    private String remarks;
    @ExcelProperty(value = "联系人")
    private String contacts;
    @ExcelProperty(value = "联系人电话")
    private String phone;
    @ExcelProperty(value = "构造消息体时的设备地址")
    private String modbussite;
}
