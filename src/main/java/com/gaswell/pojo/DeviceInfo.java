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
 * @Date: 2022/01/16/ 18:11
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "deviceinfo")
public class DeviceInfo {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "设备ID")
    private String deviceId;
    @ExcelProperty(value = "设备IP")
    private String deviceIp;
    @ExcelProperty(value = "端口")
    private String devicePort;
    @ExcelProperty(value = "设备上线时间")
    private String onlineTime;
    @ExcelProperty(value = "备注") // 备注信息，设备别名
    private String remark;

}
