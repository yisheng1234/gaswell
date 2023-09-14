package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaswell.mapper.SsscsjMapper;
import com.gaswell.pojo.Ssscsj;
import com.gaswell.service.SsscsjService;
import com.gaswell.utils.DeletePrefixUtils;
import com.gaswell.utils.PojoConvertParamsUtils;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.SsscsjParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/10/ 14:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class SsscsjServiceImpl extends ServiceImpl<SsscsjMapper, Ssscsj> implements SsscsjService {
    @Autowired
    private SsscsjMapper ssscsjMapper;

    @Override
    public Result insertOne(Ssscsj ssscsj) {
        ssscsjMapper.insert(ssscsj);
        return Result.success(null);
    }
    @Override
    public Result updateOne(Ssscsj ssscsj) {
        ssscsjMapper.updateById(ssscsj);
        return Result.success(null);
    }

    @Override
    public Result selectProperties(int current,int size,Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        SsscsjParam ssscsjParam = new SsscsjParam();
        Object object = PojoConvertParamsUtils.convert(ssscsj, ssscsjParam);

        //object转换为实体类
        ObjectMapper objectMapper = new ObjectMapper();
        ssscsjParam = objectMapper.convertValue(object, SsscsjParam.class);
        QueryWrapper<Ssscsj> queryWrapper = new QueryWrapper<>();

        Class cls = ssscsjParam.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String fName = f.getName();
            //有些属性是private，不能直接通过反射调用，通过下面方法解决
            f.setAccessible(true);
            try {
                if (f.get(ssscsjParam) != null) {
                    Double value = Double.parseDouble(f.get(ssscsjParam).toString());
                    if (StringUtils.contains(fName, "down")) {
                        fName = DeletePrefixUtils.deleteDown(fName);
                        //加入条件大于等于(ge)  down
                        queryWrapper.ge(fName, value);
                    } else if (StringUtils.contains(fName, "up")) {
                        fName = DeletePrefixUtils.deleteUp(fName);
                        //加入条件小于等于(le)   up
                        queryWrapper.le(fName, value);
                    } else {
                        //等于
                        queryWrapper.eq(fName, value);
                    }
                }
            }catch (Exception e){
                queryWrapper.eq(fName,f.get(ssscsjParam));
            }

        }
        IPage<Ssscsj> page=new Page<>(current,size);
        IPage<Ssscsj> page1 = ssscsjMapper.selectPage(page, queryWrapper);
//        Integer total=ssscsjMapper.selectCount(queryWrapper);
//        return  Result.success(page1.getRecords(),total);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }

    @Override
    public IPage<Ssscsj> selectByPropertiesPage(Integer current, Integer size, Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        SsscsjParam ssscsjParam = new SsscsjParam();
        Object object = PojoConvertParamsUtils.convert(ssscsj, ssscsjParam);

        //object转换为实体类
        ObjectMapper objectMapper = new ObjectMapper();
        ssscsjParam = objectMapper.convertValue(object, SsscsjParam.class);
        QueryWrapper<Ssscsj> queryWrapper = new QueryWrapper<>();

        Class cls = ssscsjParam.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String fName = f.getName();
            f.setAccessible(true);
            try {
                if (f.get(ssscsjParam) != null) {
                    Double value = Double.parseDouble(f.get(ssscsjParam).toString());
                    if (StringUtils.contains(fName, "down")) {
                        fName = DeletePrefixUtils.deleteDown(fName);
                        //加入条件大于等于(ge)  down
                        queryWrapper.ge(fName, value);
                    } else if (StringUtils.contains(fName, "up")) {
                        fName = DeletePrefixUtils.deleteUp(fName);
                        //加入条件小于等于(le)   up
                        queryWrapper.le(fName, value);
                    } else {
                        //等于
                        queryWrapper.eq(fName, value);
                    }
                }
            }catch (Exception e){
                queryWrapper.eq(fName,f.get(ssscsjParam));
            }

        }
        IPage<Ssscsj> page = new Page<Ssscsj>(current,size);

        ssscsjMapper.selectPage(page,queryWrapper);
        return page;
    }

    @Override
    public Result selectAll(int current,int size) {
        if(current!=-1){
            IPage<Ssscsj> page=new Page<>(current,size);
            IPage<Ssscsj> page1 = ssscsjMapper.selectPage(page, null);
            return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
        }else {
            List<Ssscsj> ssscsjs = ssscsjMapper.selectList(null);
            return Result.success(ssscsjs);
        }
    }

    @Override
    public Result insertBatch(List<Ssscsj> list) {
        ssscsjMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public IPage<Ssscsj> getPage(int current, int size) {
        IPage<Ssscsj> page = new Page<Ssscsj>(current,size);
        ssscsjMapper.selectPage(page,null);
        return page;
    }

}
