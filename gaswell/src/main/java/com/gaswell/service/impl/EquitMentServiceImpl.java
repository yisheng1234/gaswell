package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.mapper.EquitMentMapper;
import com.gaswell.pojo.EquipMent;
import com.gaswell.service.EquitMentService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class EquitMentServiceImpl implements EquitMentService {
    @Autowired
    private EquitMentMapper equipMentMapper;
    @Override
    public Result insert(EquipMent equipMent) {
        equipMentMapper.insert(equipMent);
        return Result.success(null);
    }

    @Override
    public Result delete(int id) {
        equipMentMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result update(EquipMent equipMent) {
        equipMentMapper.updateById(equipMent);
        return Result.success(null);
    }

    @Override
    public Result select(int size, int current, EquipMent equipMent) {
        IPage<EquipMent> page1=new Page<>(current,size);
        equipMentMapper.selectPage(page1, null);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }

}
