package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qdb02;
import com.gaswell.mapper.Qdb02Mapper;
import com.gaswell.service.IQdb02Service;
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
public class Qdb02ServiceImpl extends ServiceImpl<Qdb02Mapper, Qdb02> implements IQdb02Service {
    @Autowired
    private Qdb02Mapper qdb02Mapper;
    @Override
    public Result insertOne(Qdb02 qdb02) {
        qdb02Mapper.insert(qdb02);
        return Result.success(qdb02);
    }

    @Override
    public Result updateOne(Qdb02 qdb02) {
        qdb02Mapper.updateById(qdb02);
        return Result.success(qdb02);
    }

    @Override
    public Result selectProperties( Qdb02 qdb02) {
        Class cls=qdb02.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qdb02> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qdb02).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qdb02).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qdb02).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qdb02).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qdb02).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qdb02> page=new Page<>(current,size);
//        qdb02Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qdb02> list = qdb02Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qdb02> page=new Page<>(current,size);
        LambdaQueryWrapper<Qdb02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qdb02::getJh,jh);
        qdb02Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
