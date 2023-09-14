package com.gaswell.service;

import com.gaswell.vo.Result;

// 见QueryDtuDataService：readSetting与writeSetting方法
public interface DeviceSettingService {

    Result writeSetting(String deviceId, Integer address, Integer content);

    Result readSetting(String deviceId, Integer address);

}
