package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qba01;
import com.gaswell.mapper.Qba01Mapper;
import com.gaswell.service.IQba01Service;
import com.gaswell.service.QjService;
import com.gaswell.vo.Qba01Vo;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采气井日数据 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qba01ServiceImpl extends ServiceImpl<Qba01Mapper, Qba01> implements IQba01Service {

    @Autowired
    private Qba01Mapper qba01Mapper;
    @Autowired
    private QjService qjService;
    @Override
    public Result insertOne(Qba01 qba01) {
        qba01Mapper.insert(qba01);
        return Result.success(qba01);
    }

    @Override
    public Result updateOne(Qba01 qba01) {
        qba01Mapper.updateById(qba01);
        return Result.success(qba01);
    }

    @Override
    public Result selectProperties(Qba01 qba01,int sort,int department) {
        Class cls = qba01.getClass();
        Field[] fields = cls.getDeclaredFields();
        QueryWrapper<Qba01> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname = field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if (type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qba01).toString())) {

                    if (fname.equals("rq")) {
                        String[] dates = field.get(qba01).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.apply("to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') >= {0}", down);
                        queryWrapper.apply("to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') <= {0}", up);

                    } else {
                        queryWrapper.eq(fname, field.get(qba01).toString());
                    }
                }
                if (type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qba01).toString()) != 0) {
                    queryWrapper.eq(fname, Double.valueOf(field.get(qba01).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qba01> page=new Page<>(current,size);
//        qba01Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
//        queryWrapper.orderByAsc("rq");
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            queryWrapper.in("JH",lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            queryWrapper.in("JH",lists);
        }
        List<Qba01> list = qba01Mapper.selectList(queryWrapper);
        if (list.size() != 0 && list != null)
            if(sort==0)
                Collections.sort(list);
            else
                Collections.sort(list,Collections.reverseOrder());
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size, String jh, int sort) {
        Page<Qba01> page = new Page<>(current, size);
        LambdaQueryWrapper<Qba01> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh), Qba01::getJh, jh);
        if(sort==0)
            lambdaQueryWrapper.last("order by to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') asc");
        else
            lambdaQueryWrapper.last("order by to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') desc");
        qba01Mapper.selectPage(page, lambdaQueryWrapper);
        return new Result(true, 200, "success", page.getRecords(), (int) page.getTotal(), (int) page.getPages());
    }

    @Override
    public Qba01 selectLastOne() {

        return qba01Mapper.selectLastOne();
    }

    @Override
    public Qba01 setByDate(String s, String jh) {
        LambdaQueryWrapper<Qba01> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Qba01::getRq, s);
        lqw.eq(Qba01::getJh, jh);
        return qba01Mapper.selectOne(lqw);
    }

    @Override
    public Qba01 setByDateLast(String jh) {
        QueryWrapper<Qba01> queryWrapper = new QueryWrapper();
        queryWrapper.eq("jh", jh);
        queryWrapper.orderByDesc("rq");
        List<Qba01> qba01s = qba01Mapper.selectList(queryWrapper);
        if (qba01s.size() > 0) {
            return qba01s.get(0);
        }
        return null;
    }

    @Override
    public Cursor<Qba01> streamQuery() {
        return null;
    }

    @Override
    public Result selectByMutiProperties(Qba01Vo qba01Vo,String date,int sort,int department) {
        int len=qba01Vo.getOptions().size();
        List<String> options=qba01Vo.getOptions();
        String property=qba01Vo.getProperty();
        LambdaQueryWrapper<Qba01> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(date)){
            String[] dates = date.split(",");
            String down = dates[0];
            String up = dates[1];
            lambdaQueryWrapper.apply("to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') >= {0}", down);
            lambdaQueryWrapper.apply("to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') <= {0}", up);
        }
        if(sort==0)
            lambdaQueryWrapper.last("order by to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') asc");
        else
            lambdaQueryWrapper.last("order by to_char(to_date(rq，'yyyy-MM-dd'),'yyyy-MM-dd') desc");
        if(len==0){
            Result.success(qba01Mapper.selectList(lambdaQueryWrapper));
        }
        if(property.equals("jh")){
            lambdaQueryWrapper.in(Qba01::getJh,options);
        }else if(property.equals("qm")){
            lambdaQueryWrapper.in(Qba01::getQm,options);
        }else if(property.equals("zm")){
            lambdaQueryWrapper.in(Qba01::getZm,options);
        }else if(property.equals("dm")){
            lambdaQueryWrapper.in(Qba01::getDm,options);
        }else if(property.equals("zyq")){
            lambdaQueryWrapper.in(Qba01::getZyq,options);
        }else if(property.equals("qkmc")){
            lambdaQueryWrapper.in(Qba01::getQcdk,options);
        }
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(Qba01::getJh,lists);
        }
        if(department==3){
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(Qba01::getJh,lists);
        }

        return Result.success(qba01Mapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public Result findQm() {
        QueryWrapper<Qba01> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct qm");
        List<Map<String, Object>> maps = qba01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
    @Override
    public Result findZm() {
        QueryWrapper<Qba01> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct zm");
        List<Map<String, Object>> maps = qba01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
    @Override
    public Result findDm() {
        QueryWrapper<Qba01> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct dm");
        List<Map<String, Object>> maps = qba01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
    @Override
    public Result findZyq() {
        QueryWrapper<Qba01> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct zyq");
        List<Map<String, Object>> maps = qba01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
    @Override
    public Result findQkmc() {
        QueryWrapper<Qba01> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct qcdk");
        List<Map<String, Object>> maps = qba01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
}
