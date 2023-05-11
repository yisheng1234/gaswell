package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.QjMapper;
import com.gaswell.mapper.ReciveCycleDataMapper;
import com.gaswell.pojo.Qj;
import com.gaswell.pojo.ReciveCycleData;
import com.gaswell.service.QjService;
import com.gaswell.service.ReciveCycleDataService;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@DS("mysql")
public class ReciveCycleDataServiceImpl extends ServiceImpl<ReciveCycleDataMapper, ReciveCycleData> implements ReciveCycleDataService {
    @Autowired
    private ReciveCycleDataMapper reciveCycleDataMapper;
    @Autowired
    private QjService qjService;
    @Autowired
    private QjMapper qjMapper;
    @Override
    public void addData(ReciveCycleData reciveCycleData) {
        reciveCycleDataMapper.insert(reciveCycleData);
    }

    @Override
    public Result findLatestData() {
        List<Qj> qjs = qjMapper.selectList(null);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date=sdf.format(new Date());
        List<ReciveCycleData> list=new ArrayList<>();

        for (Qj qj : qjs) {
            String dateAndywjh=qj.getYwbh()+"_"+date;
            DynamicTableNameThreadLocal.put(dateAndywjh);
            ReciveCycleData reciveCycleData=reciveCycleDataMapper.findLatestData();
            list.add(reciveCycleData);

        }
        return Result.success(list);
    }

    @Override
    public Result showData(String ywbh, String properties, String date) {


        DynamicTableNameThreadLocal.put(ywbh+"_"+date+"_backup");
        if(properties.equals("ytyc")){
            properties="(ygyl-tgyl)";
            return  Result.success(reciveCycleDataMapper.showDataYtyc(properties));
        }
        return Result.success(reciveCycleDataMapper.showData(properties));
    }

    @Override
    public Result insertOne(ReciveCycleData reciveCycleData, String deviceId) {
        // TODO 先从qj表根据txbh（即deviceId）查询气井编号（id、英文编号、中文编号），再根据气井编号判断插入到哪张表中，同时判断当前月份的表是否创建，如果没创建需要根据当前月份创建一张表（也可以采用定时任务自动创建表）
        Calendar now = Calendar.getInstance();
        int  year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        Qj qj = (Qj) qjService.selectByTxbh(deviceId).getData();
//        DynamicTableNameThreadLocal.put(qj.getYwbh());
        String tableName = "ssscsj_" + qj.getYwbh() + "_" + year + month;
        boolean isTableExist = reciveCycleDataMapper.tableIsExist(tableName);
        if(!isTableExist){
            reciveCycleDataMapper.createTable(tableName);
        }


        return null;
    }

    @Override
    public Result updateOne(ReciveCycleData reciveCycleData, String ywjh, String date) {
        DynamicTableNameThreadLocal.put(ywjh+"_"+date);
        reciveCycleDataMapper.updateById(reciveCycleData);
        return Result.success(null);
    }


    @Override
    public Result selectProperties(int current, int size, ReciveCycleData reciveCycleData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Result selectAll(int current, int size) {
        return null;
    }

    @Override
    public Result insertBatch(List<ReciveCycleData> list) {
        return null;
    }

    @Override
    public IPage<ReciveCycleData> getPage(int current, int size) {
        return null;
    }

    @Override
    public IPage<ReciveCycleData> selectByPropertiesPage(Integer current, Integer size, ReciveCycleData reciveCycleData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Result deleteData(String ywbh, String date, Integer id) {
        DynamicTableNameThreadLocal.put(ywbh+"_"+date);
        reciveCycleDataMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public ReciveCycleData selectLatestNormalDataByYwbh(String ywbh) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date=sdf.format(new Date());
        DynamicTableNameThreadLocal.put(ywbh+"_"+date);
        ReciveCycleData reciveCycleData = reciveCycleDataMapper.findLastNorm();
        return reciveCycleData;
    }

    @Override
    public Result findOneDayData(String ywjh, String day) {
        String[] dates=day.split("-");
        String date=dates[0]+dates[1];
        String down=day+" 00:00:00";
        String up=day+" 24:00:00";
        DynamicTableNameThreadLocal.put(ywjh+"_"+date);
        System.out.println(ywjh);
        return  Result.success(reciveCycleDataMapper.findOneDayData(down,up));
    }

    @Override
    public Result selectData(String ywbh, String date, String properties) {
        String[] str=date.split("-");
        String ny=str[0]+str[1];
        System.out.println(ny);
        String[] dates=date.split(",");
        String down=dates[0];
        String up=dates[1];
        DynamicTableNameThreadLocal.put(ywbh+"_"+ny+"_backup");
        if(properties.equals("ytyc")){
            properties="(ygyl-tgyl)";
            return Result.success(reciveCycleDataMapper.selectDataYtyc(down,up,properties));
        }
        return Result.success(reciveCycleDataMapper.selectData(down,up,properties));
    }

    @Override
    public Result workingCondition(String properties, int number,String ywjh) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date=sdf.format(new Date());
        String dateAndywjh=ywjh+"_"+date;
        DynamicTableNameThreadLocal.put(dateAndywjh);
        ReciveCycleData reciveCycleData=reciveCycleDataMapper.findLatestData();
        int lastId=reciveCycleData.getId();

        Method m = reciveCycleData.getClass().getMethod("get" + properties);
        String value = m.invoke(reciveCycleData).toString();

        int id=reciveCycleDataMapper.selectIdByProperties(properties,value);

        if(number>(lastId-id+1))
            return Result.success(null);
        else
            return Result.success(reciveCycleData);
    }

}
