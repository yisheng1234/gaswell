package com.gaswell.service;

import com.gaswell.pojo.AlarmRecord;
import com.gaswell.vo.Result;

public interface AlarmRecordService {
    Result selectAll(int department);

    Result updateOne(AlarmRecord alarmRecord);
}
