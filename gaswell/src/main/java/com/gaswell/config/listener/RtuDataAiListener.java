package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.RtuDataAi;
import com.gaswell.service.RtuDataAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")   //设置为多例， prototype是固定的， 官方要求
public class RtuDataAiListener extends AnalysisEventListener<RtuDataAi> {

    @Autowired
    private RtuDataAiService rtuDataAiService;
    List<RtuDataAi> list=new ArrayList<>();

    @Override
    public void invoke(RtuDataAi rtuDataAi, AnalysisContext analysisContext) {
        list.add(rtuDataAi);
        if(list.size()%200==0){
            // 存入数据库
            rtuDataAiService.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        rtuDataAiService.insertBatch(list);
        list.clear();
    }
}
