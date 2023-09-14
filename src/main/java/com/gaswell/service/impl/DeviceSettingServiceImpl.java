package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.mapper.DeviceInfoMapper;
import com.gaswell.service.DeviceSettingService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class DeviceSettingServiceImpl implements DeviceSettingService {
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public Result writeSetting(String deviceId, Integer address, Integer content) {
        return null;
    }

    @Override
    public Result readSetting(String deviceId, Integer address) {
        return null;
    }
}
