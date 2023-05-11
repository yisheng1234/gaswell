package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.mapper.AlarmCensusMapper;
import com.gaswell.mapper.AlarmRecordMapper;
import com.gaswell.pojo.AlarmCensus;
import com.gaswell.pojo.AlarmRecord;
import com.gaswell.service.AlarmCensusService;
import com.gaswell.service.QjService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("mysql")
public class AlarmCensusServiceImpl implements AlarmCensusService {
    @Autowired
    private AlarmRecordMapper alarmRecordMapper;
    @Autowired
    private AlarmCensusMapper alarmCensusMapper;
    @Autowired
    private QjService qjService;
    @Override
    public void census() {
        List<String> jhs = alarmRecordMapper.selectAllJh();
        List<String> records = alarmRecordMapper.selectAllRecord();
        List<String> categorys = alarmRecordMapper.selectAllCategory();

        for (String jh : jhs)
            for (String record : records)
                for (String category : categorys) {
                    LambdaQueryWrapper<AlarmRecord> lqw=new LambdaQueryWrapper<>();
                    LambdaQueryWrapper<AlarmCensus> lwq1=new LambdaQueryWrapper<>();
                    lqw.eq(AlarmRecord::getJh,jh)
                            .eq(AlarmRecord::getRecord,record)
                            .eq(AlarmRecord::getAlarm_category,category);
                    lwq1.eq(AlarmCensus::getJh,jh)
                            .eq(AlarmCensus::getRecord,record)
                            .eq(AlarmCensus::getAlarm_category,category);
                    List<AlarmRecord> alarmRecords = alarmRecordMapper.selectList(lqw);
                    AlarmCensus alarmCensus1 = alarmCensusMapper.selectOne(lwq1);
                    AlarmCensus alarmCensus = new AlarmCensus();
                    if(alarmCensus1!=null){
                        alarmCensus.setStatus(alarmCensus1.getStatus());
                        alarmCensus.setPerson(alarmCensus1.getPerson());
                        alarmCensus.setOp_time(alarmCensus1.getOp_time());
                        alarmCensus.setOp_status(alarmCensus1.getOp_status());
                        alarmCensus.setRemarks(alarmCensus1.getRemarks());
                        alarmCensusMapper.delete(lwq1);
                    }
                    if(alarmRecords.size()!=0) {
                        int len = alarmRecords.size();
                        alarmCensus.setAlarm_category(category);
                        if(category.equals("数据偏离趋势") ||  category.equals("数据丢失") || category.equals("数据失真"))
                            alarmCensus.setCategory("数据异常");
                        else
                            alarmCensus.setCategory("工况异常");
                        alarmCensus.setTimes(len);
                        alarmCensus.setRecord(record);
                        alarmCensus.setJh(jh);
                        alarmCensus.setStart_time(alarmRecords.get(0).getTime());
                        alarmCensus.setEnd_time(alarmRecords.get(len - 1).getTime());
                        alarmCensusMapper.insert(alarmCensus);
                    }
                }
    }

    @Override
    public Result selectAll(int department) {
        LambdaQueryWrapper<AlarmCensus> lambdaQueryWrapper=new LambdaQueryWrapper<>();

        if(department==2) {
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(AlarmCensus::getJh,lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(AlarmCensus::getJh,lists);
        }

       return Result.success(alarmCensusMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public Result updateOne(AlarmCensus alarmCensus) {
        alarmCensusMapper.updateById(alarmCensus);
        return Result.success(null);
    }
}
