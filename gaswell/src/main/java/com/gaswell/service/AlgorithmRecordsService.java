package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.AlgorithmRecords;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.AlgorithmRecordsParams;

public interface AlgorithmRecordsService extends IService<AlgorithmRecords> {
    Result selectByProperties(AlgorithmRecords algorithmRecords);

    Result deleteByProperties(AlgorithmRecordsParams algorithmRecordsParams);

    // 根据类型（积液、冻堵、间开、泡排剂、甲醇），查询测试集分数最高的算法
    Result selectByTypeHightest(AlgorithmRecords algorithmRecords);

    Result updateUsedAlgorithm(String algorithm_type, String algorithm_name);
}
