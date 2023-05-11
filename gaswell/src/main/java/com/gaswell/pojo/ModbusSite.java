package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("modbussite")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModbusSite {
    private String paramName;
    private String dtu;
    private String rtu;
    private String bz;
    private String value;
    private String datatype;
}
