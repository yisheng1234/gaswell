package com.gaswell.service;

import com.gaswell.pojo.AlarmCensus;
import com.gaswell.vo.Result;

public interface AlarmCensusService {
    void census();

    Result selectAll(int department);

    Result updateOne(AlarmCensus alarmCensus);
}
