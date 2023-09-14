package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qbb01;
import com.gaswell.mapper.Qbb01Mapper;
import com.gaswell.service.IQbb01Service;
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
public class Qbb01ServiceImpl extends ServiceImpl<Qbb01Mapper, Qbb01> implements IQbb01Service {
    @Autowired
    private Qbb01Mapper qbb01Mapper;
    @Override
    public Result insertOne(Qbb01 qbb01) {
        qbb01Mapper.insert(qbb01);
        return Result.success(qbb01);
    }

    @Override
    public Result updateOne(Qbb01 qbb01) {
        qbb01Mapper.updateById(qbb01);
        return Result.success(qbb01);
    }

    @Override
    public Result selectProperties( Qbb01 qbb01) {
        Class cls=qbb01.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qbb01> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qbb01).toString())){
                    if(fname.equals("ny")){
                        String[] dates = field.get(qbb01).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qbb01).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qbb01).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qbb01).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qbb01> page=new Page<>(current,size);
//        qbb01Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qbb01> list = qbb01Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qbb01> page=new Page<>(current,size);
        LambdaQueryWrapper<Qbb01> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qbb01::getQtmc,jh);
        qbb01Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
