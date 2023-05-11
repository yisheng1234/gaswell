package com.gaswell.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmRecordsParams {
    private Integer record_id;
    private Integer user_id;
    private String algorithm_type;
    private String algorithm_name;
    private String database_type;
    private String database_condition_type;
    private String database_condition;
}
