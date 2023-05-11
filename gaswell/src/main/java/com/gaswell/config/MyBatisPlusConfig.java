package com.gaswell.config;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.utils.EasySqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@MapperScan("com.gaswell.mapper")
public class MyBatisPlusConfig  {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor=new DynamicTableNameInnerInterceptor();

        HashMap<String, TableNameHandler> map=new HashMap<>();
        map.put("rscsj",(sql,tableName)->{
            String ywjh= DynamicTableNameThreadLocal.get();

            return ywjh+"_day";
        });
        map.put("yscsj",(sql, tableName) -> {
            String ywjh= DynamicTableNameThreadLocal.get();

            return ywjh+"_month";
        });
        map.put("realtimedata",(sql,tableName)->{
            String ywjhAndDate = DynamicTableNameThreadLocal.get();
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
//            String date=sdf.format(new Date());
            // return "recivecycledata_"+dateAndywjh;
            return ywjhAndDate;
        });
        map.put("recivecycledata",(sql,tableName)->{
            String ywjhAndDate = DynamicTableNameThreadLocal.get();
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
//            String date=sdf.format(new Date());
            // return "recivecycledata_"+dateAndywjh;
            return ywjhAndDate;
        });
        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    @Bean
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }


}
