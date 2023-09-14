package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.ReciveCycleData;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ReciveCycleDataService extends IService<ReciveCycleData> {

    void addData(ReciveCycleData reciveCycleData);

    Result findLatestData();

    Result showData(String ywbh, String properties, String date);


    Result insertOne(ReciveCycleData reciveCycleData, String deviceId);

    Result updateOne(ReciveCycleData reciveCycleData, String ywjh, String date);

    Result selectProperties(int current,int size,ReciveCycleData reciveCycleData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size);

    Result insertBatch(List<ReciveCycleData> list);

    IPage<ReciveCycleData> getPage(int current, int size);

    IPage<ReciveCycleData> selectByPropertiesPage(Integer current, Integer size, ReciveCycleData reciveCycleData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result deleteData(String ywbh, String date, Integer id);

    ReciveCycleData selectLatestNormalDataByYwbh(String ywbh);

    Result findOneDayData(String ywjh, String day);

    Result selectData(String ywbh, String date,String properties);

    Result workingCondition(String properties, int number, String ywjh) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
