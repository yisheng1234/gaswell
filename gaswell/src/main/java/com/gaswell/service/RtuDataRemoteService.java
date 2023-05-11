package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.RtuData;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RtuDataParams;


public interface RtuDataRemoteService extends IService<RtuData> {

    Result selectPropertiesByPage(int current, int size, RtuDataParams rtuDataParams);

    Result selectProperties(RtuDataParams rtuDataParams);

    Result selectCount();

    Result selectAll();

    Result selectByRange(int countLocal, int countRemote);

}
