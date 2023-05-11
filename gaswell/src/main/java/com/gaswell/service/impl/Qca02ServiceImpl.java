package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qca02;
import com.gaswell.mapper.Qca02Mapper;
import com.gaswell.service.IQca02Service;
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
public class Qca02ServiceImpl extends ServiceImpl<Qca02Mapper, Qca02> implements IQca02Service {
    @Autowired
    private Qca02Mapper qca02Mapper;
    @Override
    public Result insertOne(Qca02 qca02) {
        qca02Mapper.insert(qca02);
        return Result.success(qca02);
    }

    @Override
    public Result updateOne(Qca02 qca02) {
        qca02Mapper.updateById(qca02);
        return Result.success(qca02);
    }

    @Override
    public Result selectProperties( Qca02 qca02) {
        Class cls=qca02.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qca02> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qca02).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qca02).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qca02).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qca02).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qca02).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qca02> page=new Page<>(current,size);
//        qca02Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qca02> list = qca02Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qca02> page=new Page<>(current,size);
        LambdaQueryWrapper<Qca02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qca02::getJh,jh);
        qca02Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
