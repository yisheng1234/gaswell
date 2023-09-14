package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.RtuData;
import com.gaswell.service.RtuDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")   //设置为多例， prototype是固定的， 官方要求
public class RtuDataListener extends AnalysisEventListener<RtuData> {

    @Autowired
    private RtuDataService rtuDataService;
    List<RtuData> list=new ArrayList<>();

    @Override
    public void invoke(RtuData rtuData, AnalysisContext analysisContext) {
        System.out.println(rtuData);
        list.add(rtuData);
        if(list.size()%200==0){
            // 存入数据库
            rtuDataService.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        rtuDataService.insertBatch(list);
        list.clear();
    }
}
