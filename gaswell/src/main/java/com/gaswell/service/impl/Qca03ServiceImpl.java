package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qca03;
import com.gaswell.mapper.Qca03Mapper;
import com.gaswell.service.IQca03Service;
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
public class Qca03ServiceImpl extends ServiceImpl<Qca03Mapper, Qca03> implements IQca03Service {
    @Autowired
    private Qca03Mapper qca03Mapper;
    @Override
    public Result insertOne(Qca03 qca03) {
        qca03Mapper.insert(qca03);
        return Result.success(qca03);
    }

    @Override
    public Result updateOne(Qca03 qca03) {
        qca03Mapper.updateById(qca03);
        return Result.success(qca03);
    }

    @Override
    public Result selectProperties( Qca03 qca03) {
        Class cls=qca03.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qca03> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qca03).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qca03).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qca03).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qca03).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qca03).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qca03> page=new Page<>(current,size);
//        qca03Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qca03> list = qca03Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qca03> page=new Page<>(current,size);
        LambdaQueryWrapper<Qca03> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qca03::getJh,jh);
        qca03Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
