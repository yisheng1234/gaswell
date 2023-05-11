package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.RtuDataAi;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RtuDataAiParams;

import java.util.List;


public interface RtuDataAiService extends IService<RtuDataAi> {
    Result selectProperties(int current, int size, RtuDataAiParams rtuDataAiParams);

    Result insertBatch(List<RtuDataAi> list);

    Result selectCount();

    // 删除（存在问题：本地删除后，与远程同步时易出错）
    Result delete(RtuDataAiParams rtuDataAiParams);

    // 修改
    Result update(RtuDataAi rtuDataAi);
}
