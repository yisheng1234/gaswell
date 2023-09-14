package com.gaswell.service;

import com.gaswell.vo.Result;

public interface ModbusLogService {
    Result selectAll(int current, int size);
}
