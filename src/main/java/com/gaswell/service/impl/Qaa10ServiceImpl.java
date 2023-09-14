package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa10;
import com.gaswell.mapper.Qaa10Mapper;
import com.gaswell.service.IQaa10Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 试气记录 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2102-05-24
 */
@Service
@DS("oracle")
public class Qaa10ServiceImpl extends ServiceImpl<Qaa10Mapper, Qaa10> implements IQaa10Service {
    @Autowired
    private Qaa10Mapper qaa10Mapper;
    @Override
    public Result insertOne(Qaa10 qaa10) {
        qaa10Mapper.insert(qaa10);
        return Result.success(qaa10);
    }

    @Override
    public Result updateOne(Qaa10 qaa10) {
        qaa10Mapper.updateById(qaa10);
        return Result.success(qaa10);
    }

    @Override
    public Result selectProperties( Qaa10 qaa10) {
        Class cls=qaa10.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qaa10> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qaa10).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qaa10).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qaa10).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qaa10).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qaa10).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qaa10> page=new Page<>(current,size);
//        qaa10Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qaa10> list = qaa10Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qaa10> page=new Page<>(current,size);
        LambdaQueryWrapper<Qaa10> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qaa10::getJh,jh);
        qaa10Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
