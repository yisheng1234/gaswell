package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.mapper.ParamsUpdateRecordMapper;
import com.gaswell.pojo.ParamsUpdateRecord;
import com.gaswell.service.ParamsUpdateRecordServie;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class ParamsUpdateRecordServiceImpl implements ParamsUpdateRecordServie {
    @Autowired
    private ParamsUpdateRecordMapper paramsUpdateRecordMapper;
    @Override
    public Result selectAll(int current,int size) {
        IPage<ParamsUpdateRecord> page=new Page<>(current,size);
        IPage<ParamsUpdateRecord> page1 = paramsUpdateRecordMapper.selectPage(page, null);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }
}
