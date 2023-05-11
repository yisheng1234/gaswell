package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("modbus_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModbusLog {
    private Integer id;
    private String jh;
    private String datatype;
    private String updateValue;
    private String modbus;
    private String iswrite;
    private String userName;
    private String time;
    private String operate;

}
