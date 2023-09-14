package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.mapper.ReciveRealTimeMapper;
import com.gaswell.pojo.ReciveRealTimeData;
import com.gaswell.service.ReciveRealTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class ReciveRealTimeDataServiceImpl implements ReciveRealTimeDataService {
    @Autowired
    private ReciveRealTimeMapper reciveRealTimeMapper;
    @Override
    public void addData(ReciveRealTimeData reciveRealTimeData) {
        reciveRealTimeMapper.insert(reciveRealTimeData);
    }
}
