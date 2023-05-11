package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gaswell.pojo.RealTimeData;
import com.gaswell.pojo.ReciveCycleData;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface RealTimeDataService {
    void addData(RealTimeData realTimeData);

    Result findLatestData();

    Result showData(String ywbh, String properties, String date);


    Result insertOne(RealTimeData realTimeData, String deviceId);

    Result updateOne(RealTimeData realTimeData,String ywjh,String date);

    Result selectProperties(int current,int size,RealTimeData realTimeData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size);

    Result insertBatch(List<RealTimeData> list);

    IPage<RealTimeData> getPage(int current, int size);

    IPage<RealTimeData> selectByPropertiesPage(Integer current, Integer size, RealTimeData realTimeData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result deleteData(String ywbh, String date, Integer id);

    ReciveCycleData selectLatestNormalDataByYwbh(String ywbh);

    Result findOneDayData(String ywjh, String day);

    Result selectData(String ywbh, String date,String properties);

    Result workingCondition(String properties, int number,String ywjh) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    Result JYPolice(String ywbh);

    Result DDPolice(String ywbh);

    Result SHWPolice(String ywbh);

    Result selectALLData(String ywbh,int current,int size);

    Result selectDataByDate(String ywbh, String date);
}
