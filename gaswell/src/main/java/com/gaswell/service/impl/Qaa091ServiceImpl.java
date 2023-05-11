package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa091;
import com.gaswell.mapper.Qaa091Mapper;
import com.gaswell.service.IQaa091Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 射孔井段数据 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 20912-05-24
 */
@Service
@DS("oracle")
public class Qaa091ServiceImpl extends ServiceImpl<Qaa091Mapper, Qaa091> implements IQaa091Service {
    @Autowired
    private Qaa091Mapper qaa091Mapper;
    @Override
    public Result insertOne(Qaa091 qaa091) {
        qaa091Mapper.insert(qaa091);
        return Result.success(qaa091);
    }

    @Override
    public Result updateOne(Qaa091 qaa091) {
        qaa091Mapper.updateById(qaa091);
        return Result.success(qaa091);
    }

    @Override
    public Result selectProperties(Qaa091 qaa091) {
        Class cls=qaa091.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qaa091> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qaa091).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qaa091).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qaa091).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qaa091).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qaa091).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qaa091> page=new Page<>(current,size);
//        qaa091Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qaa091> list = qaa091Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qaa091> page=new Page<>(current,size);
        LambdaQueryWrapper<Qaa091> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qaa091::getJh,jh);
        qaa091Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
