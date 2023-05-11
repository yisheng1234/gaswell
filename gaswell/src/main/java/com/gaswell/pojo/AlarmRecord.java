package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("alarm_record")
public class AlarmRecord {
    private Integer id;
    private String jh;
    private String time;
    private String alarm_category;
    private String record;
}
