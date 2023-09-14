package com.gaswell.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gaswell.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")   //设置为多例， prototype是固定的， 官方要求
public class UserListener extends AnalysisEventListener<User> {

    List<User> list=new ArrayList<>();
    /**
     * @param user: 每次读取到的数据封装的对象
     * @param analysisContext:
     * @return void
     * @author yisheng
     * @description TODO
     * @date 2021-12-09 10:25
     */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {

        list.add(user);
        if(list.size()%10==0){
           // 存入数据库

            list.clear();
        }
    }

    @Override
/**
 * @param analysisContext: 读取完整个文档之后调用的方法
 * @return void
 * @author yisheng
 * @description TODO
 * @date 2021-12-09 10:26
 */
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
