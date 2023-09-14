package com.gaswell.service;

import com.gaswell.vo.Result;

public interface QueryDtuDataService {

    // 批量向在线设备发送（一次性查询全部数据）指令
    Result queryBatchDtuData();

    // 向单个在线设备发送（一次性查询全部数据）指令
    Result queryDtuData(String deviceId);

    // 批量向在线设备发送（逐一查询全部数据）指令
    Result queryBatchDtuDataSingle();

    // 向单个在线设备发送（逐一查询全部数据）指令
    Result queryDtuDataSingle(String deviceId);

    // 从指定设备指定地址读取数据（设备参数）
    Result readSetting(String deviceId, Integer address);

    // 向指定设备指定地址写入指定数据（设备参数）
    Result writeSetting(String deviceId, Integer address, Integer content);

}
