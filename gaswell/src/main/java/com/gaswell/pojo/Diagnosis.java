package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("diagnosis")
public class Diagnosis {
    private String jh;
    private String cjsj;
    private String category;
    private String record;
}
