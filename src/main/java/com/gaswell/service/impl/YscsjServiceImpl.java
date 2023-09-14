package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaswell.mapper.YscsjMapper;
import com.gaswell.pojo.Yscsj;
import com.gaswell.service.YscsjService;
import com.gaswell.utils.DeletePrefixUtils;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.utils.PojoConvertParamsUtils;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.YscsjParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:43
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class YscsjServiceImpl extends ServiceImpl<YscsjMapper, Yscsj> implements YscsjService {
    @Autowired
    private YscsjMapper yscsjMapper;

    @Override
    public Result insertOne(Yscsj yscsj,String ywjh) {
        if(ywjh!=null){
            DynamicTableNameThreadLocal.put(ywjh);
            yscsjMapper.insert(yscsj);
            return Result.success(null);
        }
        else{
            return Result.fail(400,"请输入英文井号");
        }
    }


    @Override
    public Result updateOne(Yscsj yscsj,String ywjh) {
        if(ywjh!=null){
            DynamicTableNameThreadLocal.put(ywjh);
            yscsjMapper.updateById(yscsj);
            return Result.success(null);
        }
        else{
            return Result.fail(400,"请输入英文井号");
        }

    }

    @Override
    public Result selectProperties(int current,int size,Yscsj yscsj,String ywjh) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(ywjh!=null){
            DynamicTableNameThreadLocal.put(ywjh);
            YscsjParam yscsjParam = new YscsjParam();
            Object object = PojoConvertParamsUtils.convert(yscsj, yscsjParam);

            //object转换为实体类
            ObjectMapper objectMapper = new ObjectMapper();
            yscsjParam = objectMapper.convertValue(object, YscsjParam.class);
            QueryWrapper<Yscsj> queryWrapper = new QueryWrapper<>();

            Class cls = yscsjParam.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fName = f.getName();
                f.setAccessible(true);
                if (f.get(yscsjParam) != null) {
                    try {
                        Double value = Double.parseDouble(f.get(yscsjParam).toString());
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
                    } catch (Exception e) {
                        queryWrapper.eq(fName,f.get(yscsjParam));
                    }
                }
            }
            IPage<Yscsj> page=new Page<>(current,size);
            IPage<Yscsj> page1 = yscsjMapper.selectPage(page, queryWrapper);
            return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
        }
        else{
            return Result.fail(400,"请输入英文井号");
        }
    }
    @Override
    public IPage<Yscsj> selectByPropertiesPage(Integer current, Integer size, Yscsj yscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        if(yscsj.getY_ywjh()!=null){
//            DynamicTableNameThreadLocal.put(yscsj.getY_ywjh());
//            YscsjParam yscsjParam = new YscsjParam();
//            Object object = PojoConvertParamsUtils.convert(yscsj, yscsjParam);
//
//            //object转换为实体类
//            ObjectMapper objectMapper = new ObjectMapper();
//            yscsjParam = objectMapper.convertValue(object, YscsjParam.class);
//            QueryWrapper<Yscsj> queryWrapper = new QueryWrapper<>();
//
//            Class cls = yscsjParam.getClass();
//            Field[] fields = cls.getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                Field f = fields[i];
//                String fName = f.getName();
//                f.setAccessible(true);
//                if (f.get(yscsjParam) != null) {
//                    try {
//                        Double value = Double.parseDouble(f.get(yscsjParam).toString());
//                        if (StringUtils.contains(fName, "down")) {
//                            fName = DeletePrefixUtils.deleteDown(fName);
//                            //加入条件大于等于(ge)  down
//                            queryWrapper.ge(fName, value);
//                        } else if (StringUtils.contains(fName, "up")) {
//                            fName = DeletePrefixUtils.deleteUp(fName);
//                            //加入条件小于等于(le)   up
//                            queryWrapper.le(fName, value);
//                        } else {
//                            //等于
//                            queryWrapper.eq(fName, value);
//                        }
//                    } catch (Exception e) {
//                        queryWrapper.eq(fName,f.get(yscsjParam));
//                    }
//                }
//
//            }
//            IPage<Yscsj> page = new Page<Yscsj>(current,size);
//            yscsjMapper.selectPage(page,queryWrapper);
//            return page;
//        }
//        else{
//            return null;
//        }
        return null;
    }

    @Override
    public Result showdata(String ywjh, String date, String properties) {
        DynamicTableNameThreadLocal.put(ywjh);

        String[] split=date.split(",");
        String date_down=split[0];
        String date_up=split[1];
        List<Map<String,String>> list=yscsjMapper.showData(properties,date_down,date_up);
        return Result.success(list);
    }

    @Override
    public Result getColumnNameAndComment(String ywjh) {
        String tableName=ywjh+"_month";
        return Result.success(yscsjMapper.getColumnNameAndComment(tableName));
    }

    @Override
    public Result selectAll(int current,int size,String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        if(current!=-1) {
            IPage<Yscsj> iPage = new Page<>(current, size);
            IPage<Yscsj> iPage1 = yscsjMapper.selectPage(iPage, null);
            Integer num=yscsjMapper.selectCount(null);
            return new Result(true,200,"success",iPage1.getRecords(),num,(num/size)+(num%size!=0?1:0));
        }else{
            return Result.success(yscsjMapper.selectList(null));
        }
    }

    @Override
    public Result selectAllByYwjh(int current,int size, String ywjh) {
        DynamicTableNameThreadLocal.put(ywjh);
        if(current!=-1) {
            IPage<Yscsj> iPage = new Page<>(current, size);
            IPage<Yscsj> iPage1 = yscsjMapper.selectPage(iPage, null);
            return new Result(true,200,"success",iPage1.getRecords(),(int)iPage1.getTotal(),(int)iPage1.getPages());
        }else{
            return Result.success(yscsjMapper.selectList(null));
        }
    }

    @Override
    public void insertBatch(List<Yscsj> list) {

            yscsjMapper.insertBatchSomeColumn(list);
    }

}
