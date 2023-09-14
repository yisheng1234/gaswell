package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qab04;
import com.gaswell.mapper.Qab04Mapper;
import com.gaswell.service.IQab04Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 气藏流体性质数据 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qab04ServiceImpl extends ServiceImpl<Qab04Mapper, Qab04> implements IQab04Service {
    @Autowired
    private Qab04Mapper qab04Mapper;
    @Override
    public Result insertOne(Qab04 qab04) {
        qab04Mapper.insert(qab04);
        return Result.success(qab04);
    }

    @Override
    public Result updateOne(Qab04 qab04) {
        qab04Mapper.updateById(qab04);
        return Result.success(qab04);
    }

    @Override
    public Result selectProperties( Qab04 qab04) {
        Class cls=qab04.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qab04> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qab04).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qab04).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qab04).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qab04).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qab04).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qab04> page=new Page<>(current,size);
//        qab04Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qab04> list = qab04Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qab04> page=new Page<>(current,size);
        LambdaQueryWrapper<Qab04> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qab04::getQt,jh);
        qab04Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
