package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.Ssscsj;
import com.gaswell.service.SsscsjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/10/ 14:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Slf4j
@Component
@Scope("prototype")   //设置为多例， prototype是固定的， 官方要求
public class SsscsjListener extends AnalysisEventListener<Ssscsj> {
    @Autowired
    private SsscsjService ssscsjService;
    List<Ssscsj> list=new ArrayList<>();
    @Override
    public void invoke(Ssscsj ssscsj, AnalysisContext analysisContext) {

        list.add(ssscsj);
        if(list.size()%200==0){
            // 存入数据库
            ssscsjService.insertBatch(list);
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ssscsjService.insertBatch(list);
        list.clear();
    }

}
