package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa01;
import com.gaswell.mapper.Qaa01Mapper;
import com.gaswell.service.IQaa01Service;
import com.gaswell.service.QjService;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 气井基础信息 服务实现类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Service
@DS("oracle")
public class Qaa01ServiceImpl extends ServiceImpl<Qaa01Mapper, Qaa01> implements IQaa01Service {
@Autowired
private Qaa01Mapper qaa01Mapper;
@Autowired
private QjService qjService;
    @Override
    public List<String> selectJh() {
        return qaa01Mapper.selectJh();
    }

    @Override
    public Result insertOne(Qaa01 qaa01) {
        qaa01Mapper.insert(qaa01);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Qaa01 qaa01) {
        qaa01Mapper.updateById(qaa01);
        return Result.success(null);
    }

    @Override
    public Result selectProperties( Qaa01 qaa01) {
        Class cls=qaa01.getClass();
        Field[] fields= cls.getDeclaredFields();
        QueryWrapper<Qaa01> queryWrapper = new QueryWrapper<>();
        for (Field field : fields) {
            String fname=field.getName();
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            try {

                if(type.equals("class java.lang.String") && StringUtils.isNotBlank(field.get(qaa01).toString())){
                    if(fname.equals("rq")){

                        String[] dates = field.get(qaa01).toString().split(",");
                        String down = dates[0];
                        String up = dates[1];
                        queryWrapper.ge(fname,down);
                        queryWrapper.le(fname,up);
                    }else {
                        queryWrapper.eq(fname, field.get(qaa01).toString());
                    }
                }
                if(type.equals("java.math.BigDecimal") && Double.valueOf(field.get(qaa01).toString())!=0){
                    queryWrapper.eq(fname,Double.valueOf(field.get(qaa01).toString()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        Page<Qaa01> page=new Page<>(current,size);
//        qaa01Mapper.selectPage(page, queryWrapper);
//        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
        List<Qaa01> list = qaa01Mapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result selectAll(int current, int size,String jh,int department) {
        Page<Qaa01> page=new Page<>(current,size);
        LambdaQueryWrapper<Qaa01> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(jh),Qaa01::getJh,jh);

        qaa01Mapper.selectPage(page, lambdaQueryWrapper);

        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());

    }

    @Override
    public Integer dataCount() {
        return qaa01Mapper.selectCount(null);
    }

    @Override
    public Result selectJh(String qm,String zm) {
        QueryWrapper<Qaa01> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT JH");
        if(StringUtils.isNotBlank(qm)) {
            queryWrapper.eq("QM", qm);
        }
        if(StringUtils.isNotBlank(zm)) {
            queryWrapper.eq("zm", zm);
        }
        List<Map<String, Object>> maps = qaa01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result selectQM() {
        QueryWrapper<Qaa01> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT QM");
        List<Map<String, Object>> maps = qaa01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result selectZM(String qm) {
        QueryWrapper<Qaa01> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT ZM");
        if(StringUtils.isNotBlank(qm)) {
            queryWrapper.eq("QM", qm);
        }
        List<Map<String, Object>> maps = qaa01Mapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Qaa01 selectOne(String jh) {
        LambdaQueryWrapper<Qaa01> lqw1=new LambdaQueryWrapper<>();
        lqw1.eq(Qaa01::getJh,jh);
        return qaa01Mapper.selectOne(lqw1);
    }
}
