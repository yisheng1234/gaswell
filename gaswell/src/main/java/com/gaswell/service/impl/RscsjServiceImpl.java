package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaswell.mapper.RscsjMapper;
import com.gaswell.pojo.Rscsj;
import com.gaswell.service.RscsjService;
import com.gaswell.utils.DeletePrefixUtils;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.utils.PojoConvertParamsUtils;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RscsjParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
@DS("mysql")
public class RscsjServiceImpl extends ServiceImpl<RscsjMapper,Rscsj> implements RscsjService {
    @Autowired
    private RscsjMapper rscsjMapper;
    @Override
    public Result insertOne(Rscsj rscsj,String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        rscsjMapper.insert(rscsj);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Rscsj rscsj,String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        rscsjMapper.updateById(rscsj);
        return Result.success(null);
    }

    @Override
    public Result selectProperties(int current,int size,Rscsj rscsj,String ywjh) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DynamicTableNameThreadLocal.put(ywjh);
        RscsjParam rscsjParam = new RscsjParam();
        Object object = PojoConvertParamsUtils.convert(rscsj, rscsjParam);

        //object转换为实体类
        ObjectMapper objectMapper = new ObjectMapper();
        rscsjParam = objectMapper.convertValue(object, RscsjParam.class);
        QueryWrapper<Rscsj> queryWrapper = new QueryWrapper<>();

        Class cls = rscsjParam.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String fName = f.getName();
            f.setAccessible(true);
            try {
                if (f.get(rscsjParam) != null) {
                    Double value = Double.parseDouble(f.get(rscsjParam).toString());
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
                queryWrapper.eq(fName,f.get(rscsjParam));
            }
        }
        IPage<Rscsj> page=new Page<>(current,size);
        IPage<Rscsj> page1 = rscsjMapper.selectPage(page, queryWrapper);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }

    @Override
    public IPage<Rscsj> selectByPropertiesPage(Integer current, Integer size, Rscsj rscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        DynamicTableNameThreadLocal.put(rscsj.getR_ywjh());
//        RscsjParam rscsjParam = new RscsjParam();
//        Object object = PojoConvertParamsUtils.convert(rscsj, rscsjParam);
//
//        //object转换为实体类
//        ObjectMapper objectMapper = new ObjectMapper();
//        rscsjParam = objectMapper.convertValue(object, RscsjParam.class);
//        QueryWrapper<Rscsj> queryWrapper = new QueryWrapper<>();
//
//        Class cls = rscsjParam.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field f = fields[i];
//            String fName = f.getName();
//            f.setAccessible(true);
//            try {
//                if (f.get(rscsjParam) != null) {
//                    Double value = Double.parseDouble(f.get(rscsjParam).toString());
//                    if (StringUtils.contains(fName, "down")) {
//                        fName = DeletePrefixUtils.deleteDown(fName);
//                        //加入条件大于等于(ge)  down
//                        queryWrapper.ge(fName, value);
//                    } else if (StringUtils.contains(fName, "up")) {
//                        fName = DeletePrefixUtils.deleteUp(fName);
//                        //加入条件小于等于(le)   up
//                        queryWrapper.le(fName, value);
//                    } else {
//                        //等于
//                        queryWrapper.eq(fName, value);
//                    }
//                }
//            }catch (Exception e){
//                queryWrapper.eq(fName,f.get(rscsjParam));
//            }
//        }
//        IPage<Rscsj> page = new Page<Rscsj>(current,size);
//        rscsjMapper.selectPage(page,queryWrapper);
//        return page;
        return null;
    }

    @Override
    public Result selectAll(int current,int size,String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        if(current!=-1){
            IPage<Rscsj> page=new Page<>(current,size);
            IPage<Rscsj> page1 = rscsjMapper.selectPage(page, null);
            Integer num=rscsjMapper.selectCount(null);
            return new Result(true,200,"success",page1.getRecords(),num,(num/size)+(num%size!=0?1:0));
        }else {

            List<Rscsj> rscsjs = rscsjMapper.selectList(null);
            return Result.success(rscsjs);
        }
    }

    @Override
    public Result insertBatch(List<Rscsj> list) {
        rscsjMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public IPage<Rscsj> getPage(int current, int size) {
        IPage<Rscsj> page = new Page<Rscsj>(current,size);
        rscsjMapper.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<Rscsj> getPageByYwjh(int current, int size, String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        IPage<Rscsj> page = new Page<Rscsj>(current,size);
        rscsjMapper.selectPage(page,null);
        return page;
    }

    @Override
    public Result selectAllByYwjh(int current,int size, String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        if(current!=-1){
            IPage<Rscsj> page=new Page<>(current,size);
            IPage<Rscsj> page1 = rscsjMapper.selectPage(page, null);
            return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
        }else {
            List<Rscsj> rscsjs = rscsjMapper.selectList(null);
            return Result.success(rscsjs);
        }
    }

    @Override
    public Result showdata(String ywjh, String date, String properties) {
        DynamicTableNameThreadLocal.put(ywjh);
        String[] split=date.split(",");
        String date_down=split[0];
        String date_up=split[1];
        List<Map<String,String>> list=rscsjMapper.showData(properties,date_down,date_up);
        return Result.success(list);
    }

    @Override
    public Result getColumnNameAndComment(String ywjh) {
        String tableName=ywjh+"_day";
        return Result.success(rscsjMapper.getColumnNameAndComment(tableName));

    }
}
