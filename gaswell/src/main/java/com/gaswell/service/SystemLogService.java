package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.SystemLog;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SystemLogService extends IService<SystemLog> {
    void insertOne(SystemLog systemLog);

    Result deleteOne(SystemLog systemLog);

    Result updateOne(SystemLog systemLog);

    Result selectByProperties(SystemLog systemLog,int current,int size) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size);

    Result insertBatch(List<SystemLog> list);

    IPage<SystemLog> getPage(int current, int size);


    Result deleteBatch(List<SystemLog> systemLogs);

    Result deleteBatchById(List<Integer> systemLogs);
}
