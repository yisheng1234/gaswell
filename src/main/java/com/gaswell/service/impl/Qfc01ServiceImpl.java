package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qfc01;
import com.gaswell.mapper.Qfc01Mapper;
import com.gaswell.service.IQfc01Service;
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
public class Qfc01ServiceImpl extends ServiceImpl<Qfc01Mapper, Qfc01> implements IQfc01Service {
    @Autowired
    private Qfc01Mapper qfc01Mapper;
    @Override
    public Result insertOne(Qfc01 qfc01) {
        qfc01Mapper.insert(qfc01);
        return Result.success(qfc01);
    }

    @Override
    public Result updateOne(Qfc01 qfc01) {
        qfc01Mapper.updateById(qfc01);
        return Result.success(qfc01);
    }

    @Override
    public Result selectProperties( Qfc01 qfc01) {
        Class cls=qfc01.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qfc01> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qfc01).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qfc01).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qfc01).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qfc01).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qfc01).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qfc01> page=new Page<>(current,size);
//        qfc01Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qfc01> list = qfc01Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qfc01> page=new Page<>(current,size);
        LambdaQueryWrapper<Qfc01> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qfc01::getJh,jh);
        qfc01Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
