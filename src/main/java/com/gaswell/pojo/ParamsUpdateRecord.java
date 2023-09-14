package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("paramsupdaterecord")
public class ParamsUpdateRecord {
    private Integer xh;
    private String userName;
    private String time;
    private String content;
}
