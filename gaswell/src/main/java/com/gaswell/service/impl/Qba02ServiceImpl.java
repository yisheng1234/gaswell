package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qba02;
import com.gaswell.mapper.Qba02Mapper;
import com.gaswell.service.IQba02Service;
import com.gaswell.service.QjService;
import com.gaswell.vo.Qba01Vo;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采气井月数据 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qba02ServiceImpl extends ServiceImpl<Qba02Mapper, Qba02> implements IQba02Service {
    @Autowired
    private Qba02Mapper qba02Mapper;
    @Autowired
    private QjService qjService;
    @Override
    public Result insertOne(Qba02 qba02) {
        qba02Mapper.insert(qba02);
        return Result.success(qba02);
    }

    @Override
    public Result updateOne(Qba02 qba02) {
        qba02Mapper.updateById(qba02);
        return Result.success(qba02);
    }



    @Override
    public Result selectProperties( Qba02 qba02,int sort,int department) {

        Class cls=qba02.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qba02> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qba02).toString())) {
                    if (fname.equals("ny")) {
                        String[] dates = field.get(qba02).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname, down);
                        queryWrapper.le(fname, up);
                    } else{
                    queryWrapper.eq(fname, field.get(qba02).toString());
                }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qba02).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qba02).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(sort==0)
            queryWrapper.last("order by ny asc");
        else
            queryWrapper.last("order by ny desc");
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            queryWrapper.in("JH",lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            queryWrapper.in("JH",lists);
        }
        List<Qba02> list = qba02Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh,int sort) {
        Page<Qba02> page=new Page<>(current,size);
        LambdaQueryWrapper<Qba02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qba02::getJh,jh);
        if(sort==0)
            lambdaQueryWrapper.last("order by ny asc");
        else
            lambdaQueryWrapper.last("order by ny desc");
        qba02Mapper.selectPage(page, lambdaQueryWrapper);

        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }

    @Override
    public Result selectByMutiProperties(Qba01Vo qba01Vo,String date,int sort,int department) {
        int len=qba01Vo.getOptions().size();
        List<String> options=qba01Vo.getOptions();
        String property=qba01Vo.getProperty();
        LambdaQueryWrapper<Qba02> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(date)){
            String[] dates = date.split(",");
            String down = dates[0];
            String up = dates[1];
            lambdaQueryWrapper.ge(Qba02::getNy, down);
            lambdaQueryWrapper.le(Qba02::getNy, up);
        }
        if(sort==0)
            lambdaQueryWrapper.last("order by ny asc");
        else
            lambdaQueryWrapper.last("order by ny desc");
        if(len==0){
            Result.success(qba02Mapper.selectList(lambdaQueryWrapper));
        }
        if(property.equals("jh")){
            lambdaQueryWrapper.in(Qba02::getJh,options);
        }else if(property.equals("zyqm")){
            lambdaQueryWrapper.in(Qba02::getQm,options);
        }else if(property.equals("zm")){
            lambdaQueryWrapper.in(Qba02::getZm,options);
        }else if(property.equals("dm")){
            lambdaQueryWrapper.in(Qba02::getDm,options);
        }else if(property.equals("qkmc")){
            lambdaQueryWrapper.in(Qba02::getQcdk,options);
        }
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(Qba02::getJh,lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(Qba02::getJh,lists);
        }

        return Result.success(qba02Mapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public Result findZyqm() {
        QueryWrapper<Qba02> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct zyqm");
        List<Map<String, Object>> maps = qba02Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findZm() {
        QueryWrapper<Qba02> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct zm");
        List<Map<String, Object>> maps = qba02Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findDm() {
        QueryWrapper<Qba02> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct dm");
        List<Map<String, Object>> maps = qba02Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findQkmc() {
        QueryWrapper<Qba02> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct qcdk");
        List<Map<String, Object>> maps = qba02Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
}
