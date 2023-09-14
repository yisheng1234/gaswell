package com.gaswell.service;

import com.gaswell.pojo.DeviceInfo;

public interface DeviceInfoService {
    void addData(DeviceInfo deviceInfo);

    void deleteByDeviceId(String deviceId);

    void updateByDeviceId(DeviceInfo deviceInfo);
}
