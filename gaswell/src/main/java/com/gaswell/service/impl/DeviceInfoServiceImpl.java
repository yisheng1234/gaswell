package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.mapper.DeviceInfoMapper;
import com.gaswell.pojo.DeviceInfo;
import com.gaswell.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Override
    public void addData(DeviceInfo deviceInfo) {
        LambdaQueryWrapper<DeviceInfo> lambdaQueryWrappe=new LambdaQueryWrapper<>();
        lambdaQueryWrappe.eq(DeviceInfo::getDeviceId,deviceInfo.getDeviceId());
        DeviceInfo deviceInfo1 = deviceInfoMapper.selectOne(lambdaQueryWrappe);
        if(deviceInfo1==null){
            deviceInfoMapper.insert(deviceInfo);
        }else{
            if(!deviceInfo1.getDeviceIp().equals(deviceInfo.getDeviceIp()) || !deviceInfo1.getDevicePort().equals(deviceInfo.getDevicePort())){
                deviceInfoMapper.update(deviceInfo,lambdaQueryWrappe);
            }
        }

    }

    @Override
    public void deleteByDeviceId(String deviceId) {
        LambdaQueryWrapper<DeviceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeviceInfo::getDeviceId, deviceId);
        deviceInfoMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void updateByDeviceId(DeviceInfo deviceInfo) {
        LambdaQueryWrapper<DeviceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeviceInfo::getDeviceId, deviceInfo.getDeviceId());
        deviceInfoMapper.update(deviceInfo,lambdaQueryWrapper);
    }
}
