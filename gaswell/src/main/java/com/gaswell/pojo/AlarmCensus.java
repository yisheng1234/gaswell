package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlarmCensus {
    private Integer id;
    private String jh;
    @TableField("start_time")
    private String start_time;
    @TableField("end_time")
    private String end_time;
    @TableField("alarm_category")
    private String alarm_category;
    private String record;
    private Integer times;
    private Integer status;
    private String person;
    @TableField("op_time")
    private String op_time;
    @TableField("op_status")
    private String op_status;
    private String remarks;
    private String category;
}
