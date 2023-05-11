package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa02;
import com.gaswell.mapper.Qaa02Mapper;
import com.gaswell.service.IQaa02Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 钻井地质信息 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qaa02ServiceImpl extends ServiceImpl<Qaa02Mapper, Qaa02> implements IQaa02Service {
    @Autowired
    private Qaa02Mapper qaa02Mapper;
    @Override
    public Result insertOne(Qaa02 qaa02) {
        qaa02Mapper.insert(qaa02);
        return Result.success(qaa02);
    }

    @Override
    public Result updateOne(Qaa02 qaa02) {
        qaa02Mapper.updateById(qaa02);
        return Result.success(qaa02);
    }

    @Override
    public Result selectProperties( Qaa02 qaa02) {
        Class cls=qaa02.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qaa02> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qaa02).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qaa02).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qaa02).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qaa02).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qaa02).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qaa02> page=new Page<>(current,size);
//        qaa02Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qaa02> list = qaa02Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qaa02> page=new Page<>(current,size);
        LambdaQueryWrapper<Qaa02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qaa02::getJh,jh);
        qaa02Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
