package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qfc02;
import com.gaswell.mapper.Qfc02Mapper;
import com.gaswell.service.IQfc02Service;
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
public class Qfc02ServiceImpl extends ServiceImpl<Qfc02Mapper, Qfc02> implements IQfc02Service {
    @Autowired
    private Qfc02Mapper qfc02Mapper;
    @Override
    public Result insertOne(Qfc02 qfc02) {
        qfc02Mapper.insert(qfc02);
        return Result.success(qfc02);
    }

    @Override
    public Result updateOne(Qfc02 qfc02) {
        qfc02Mapper.updateById(qfc02);
        return Result.success(qfc02);
    }

    @Override
    public Result selectProperties( Qfc02 qfc02) {
        Class cls=qfc02.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qfc02> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qfc02).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qfc02).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qfc02).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qfc02).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qfc02).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qfc02> page=new Page<>(current,size);
//        qfc02Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qfc02> list = qfc02Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qfc02> page=new Page<>(current,size);
        LambdaQueryWrapper<Qfc02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qfc02::getJh,jh);
        qfc02Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
