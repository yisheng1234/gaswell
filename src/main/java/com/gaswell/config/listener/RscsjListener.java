package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.Rscsj;
import com.gaswell.service.RscsjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")   //设置为多例， prototype是固定的， 官方要求
public class RscsjListener extends AnalysisEventListener<Rscsj> {
    @Autowired
    private RscsjService rscsjService;
    List<Rscsj> list=new ArrayList<>();

    @Override
    public void invoke(Rscsj rscsj, AnalysisContext analysisContext) {

        list.add(rscsj);
        if(list.size()%200==0){
            // 存入数据库
            rscsjService.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        rscsjService.insertBatch(list);
        list.clear();
    }
}
