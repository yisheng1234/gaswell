package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.Qj;
import com.gaswell.service.QjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class QjListener extends AnalysisEventListener<Qj> {
    @Autowired
    private QjService qjService;
    List<Qj> list=new ArrayList<>();
    @Override
    public void invoke(Qj qj, AnalysisContext analysisContext) {
//        list.add(qj);
//        if(list.size()%200==0){
//            qjService.insertBatch(list);
//            list.clear();
//        }
        qjService.insertOne(qj);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        qjService.insertBatch(list);
//        list.clear();
    }
}
