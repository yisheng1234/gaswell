package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.Yscsj;
import com.gaswell.service.YscsjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class YscsjListener extends AnalysisEventListener<Yscsj> {
    @Autowired
    private YscsjService yscsjService;

    List<Yscsj> list=new ArrayList<>();
    @Override
    public void invoke(Yscsj yscsj, AnalysisContext analysisContext) {
        list.add(yscsj);
        if(list.size()%200==0){
            // 存入数据库
            yscsjService.insertBatch(list);
            list.clear();
        }

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            yscsjService.insertBatch(list);
            list.clear();
    }
}
