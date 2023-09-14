package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qca05;
import com.gaswell.mapper.Qca05Mapper;
import com.gaswell.service.IQca05Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qca05ServiceImpl extends ServiceImpl<Qca05Mapper, Qca05> implements IQca05Service {
    @Autowired
    private Qca05Mapper qca05Mapper;
    @Override
    public Result insertOne(Qca05 qca05) {
        qca05Mapper.insert(qca05);
        return Result.success(qca05);
    }

    @Override
    public Result updateOne(Qca05 qca05) {
        qca05Mapper.updateById(qca05);
        return Result.success(qca05);
    }

    @Override
    public Result selectProperties( Qca05 qca05) {
        Class cls=qca05.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qca05> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qca05).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qca05).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qca05).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qca05).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qca05).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qca05> page=new Page<>(current,size);
//        qca05Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qca05> list = qca05Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qca05> page=new Page<>(current,size);
        LambdaQueryWrapper<Qca05> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qca05::getJh,jh);
        qca05Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
