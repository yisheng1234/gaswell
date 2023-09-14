package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.mapper.AlarmRecordMapper;
import com.gaswell.pojo.AlarmRecord;
import com.gaswell.service.AlarmRecordService;
import com.gaswell.service.QjService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("mysql")
public class AlarmRecordServiceImpl implements AlarmRecordService {
    @Autowired
    private AlarmRecordMapper alarmRecordMapper;
    @Autowired
    private QjService qjService;

    @Override
    public Result selectAll(int department) {
        LambdaQueryWrapper<AlarmRecord> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(AlarmRecord::getJh,lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(AlarmRecord::getJh,lists);
        }
       return Result.success(alarmRecordMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public Result updateOne(AlarmRecord alarmRecord) {
        alarmRecordMapper.updateById(alarmRecord);
        return Result.success(null);
    }
}
