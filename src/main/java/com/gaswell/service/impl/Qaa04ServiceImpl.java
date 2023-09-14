package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa04;
import com.gaswell.mapper.Qaa04Mapper;
import com.gaswell.service.IQaa04Service;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 套管记录 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2042-05-24
 */
@Service
@DS("oracle")
public class Qaa04ServiceImpl extends ServiceImpl<Qaa04Mapper, Qaa04> implements IQaa04Service {
    @Autowired
    private Qaa04Mapper qaa04Mapper;
    @Override
    public Result insertOne(Qaa04 qaa04) {
        qaa04Mapper.insert(qaa04);
        return Result.success(qaa04);
    }

    @Override
    public Result updateOne(Qaa04 qaa04) {
        qaa04Mapper.updateById(qaa04);
        return Result.success(qaa04);
    }

    @Override
    public Result selectProperties( Qaa04 qaa04) {
        Class cls=qaa04.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qaa04> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qaa04).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qaa04).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qaa04).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qaa04).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qaa04).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qaa04> page=new Page<>(current,size);
//        qaa04Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qaa04> list = qaa04Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh) {
        Page<Qaa04> page=new Page<>(current,size);
        LambdaQueryWrapper<Qaa04> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qaa04::getJh,jh);
        qaa04Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
