package com.gaswell.service;

import com.gaswell.pojo.RtuData;
import com.gaswell.vo.Result;
import com.gaswell.vo.RtuDataVo;

import java.util.List;


public interface RtuDataService{

//    Result selectProperties(int current, int size, RtuDataParams rtuDataParams);
    Result selectByOptions(RtuData rtuData,int department);

    Result insertBatch(List<RtuData> list);

    Result selectCount();

    Result selectAll(int department);

    Object list();

    Result selectData(String ywbh, String date, String properties);

    Result findLatestData(int department);

    /**
     * 找最新的信息，而并非使用日期
     * @return
     */
    Result findLatestData2(int department);

    List<RtuData> selectList(int department);

    Result selectByMutiOptions(RtuDataVo rtuDataVo,String date,int sort,int department);
}
