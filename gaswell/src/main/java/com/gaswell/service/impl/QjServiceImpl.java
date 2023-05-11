package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.entity.Qaa01;
import com.gaswell.mapper.QjMapper;
import com.gaswell.mapper.RealTimeDataMapper;
import com.gaswell.mapper.RscsjMapper;
import com.gaswell.mapper.YscsjMapper;
import com.gaswell.pojo.Qj;
import com.gaswell.service.IQaa01Service;
import com.gaswell.service.QjService;
import com.gaswell.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DS("mysql")
public class QjServiceImpl extends ServiceImpl<QjMapper, Qj>  implements QjService {
    private static List<String> jhList;
    @Autowired
    private QjMapper qjMapper;
    @Autowired
    private RscsjMapper rscsjMapper;
    @Autowired
    private YscsjMapper yscsjMapper;
    @Autowired
    RealTimeDataMapper realTimeDataMapper;
    @Autowired
    private IQaa01Service qaa01Service;
    @Override
    public Result insertOne(Qj qj) {
        qjMapper.insert(qj);
//        String tableName_day=qj.getYwbh()+"_day";
//        String tableName_month=qj.getYwbh()+"_month";
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
//        String date=sdf.format(new Date());
//
//        String tableName_rcd=qj.getYwbh()+"_"+date;
//        String tableName_rcd_backup=qj.getYwbh()+"_"+date+"_backup";
//
//        if (!rscsjMapper.tableIsExist(tableName_day)) {
//            rscsjMapper.createTable(tableName_day);
//        }
//
//        if(!yscsjMapper.tableIsExist(tableName_month)) {
//            yscsjMapper.createTable(tableName_month);
//        }
//        if(!realTimeDataMapper.tableIsExist(tableName_rcd)){
//            realTimeDataMapper.createTable(tableName_rcd);
//        }
//        if(!realTimeDataMapper.tableIsExist(tableName_rcd_backup)){
//            realTimeDataMapper.createTable_backup(tableName_rcd_backup);
//        }
        return Result.success(null);
    }

    @Override
    public Result deleteOne(String ywbh) {
        QueryWrapper<Qj> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ywbh",ywbh);
        qjMapper.delete(queryWrapper);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Qj qj) {
        qjMapper.updateById(qj);
        return Result.success(null);
    }

    @Override
    public Result findAll(int current,int size,int department) {
        IPage<Qj> page=new Page<>(current,size);
        LambdaQueryWrapper<Qj> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(department==2){
            List<String> jhList1=qjMapper.findJhByYm("川渝");
            lambdaQueryWrapper.in(Qj::getYwbh,jhList1);
        }
        if(department==3){
            List<String> jhList1=qjMapper.findJhByYm("大庆");
            lambdaQueryWrapper.in(Qj::getYwbh,jhList1);
        }
        qjMapper.selectPage(page,lambdaQueryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }

    @Override
    public Result selectByProperties(int current, int size, Qj qj) {
        Class cls=qj.getClass();
        Field[] fields=cls.getDeclaredFields();
        QueryWrapper<Qj> queryWrapper=new QueryWrapper<>();
        for (int i = 0; i < fields.length; i++) {
            Field f=fields[i];
            String pName=f.getName();
            f.setAccessible(true);
            try {

                if(StringUtils.isNotBlank(f.get(qj).toString()) && !pName.equals("id")){
                    queryWrapper.eq(pName,f.get(qj).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        IPage<Qj> page=new Page<>(current,size);
        qjMapper.selectPage(page,queryWrapper);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }

    @Override
    public void insertBatch(List<Qj> list) {
        qjMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Result selectByTxbh(String txbh) {
        QueryWrapper<Qj> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("txbh",txbh);
        Qj qj = qjMapper.selectOne(queryWrapper);
        return Result.success(qj);
    }

    @Override
    public String findByTxbh(String txbh) {
        QueryWrapper<Qj> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("txbh",txbh);
        Qj qj = qjMapper.selectOne(queryWrapper);
        return qj.getYwbh();
    }

    @Override
    public List<Qj> selectAll() {
        List<Qj> qjs = qjMapper.selectList(null);
        return qjs;
    }

    @Override
    public Qj findQjByTxbh(String txbh) {
        QueryWrapper<Qj> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("txbh",txbh);
        Qj qj = qjMapper.selectOne(queryWrapper);
        return qj;
    }

    @Override
    public Result readJhFromOracle() {
        List<String> list=qaa01Service.selectJh();
        for(String jh:list){
            LambdaQueryWrapper<Qj> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Qj::getYwbh,jh);
            if(qjMapper.selectOne(lqw)==null){
                Qaa01 qaa01=qaa01Service.selectOne(jh);
                Qj qj=new Qj();
                qj.setYwbh(jh);
                qj.setZwbh(qaa01.getHzjh());
                qj.setZm(qaa01.getZm());
                qj.setScq(qaa01.getQm());
                qjMapper.insert(qj);
            }
        }

        return Result.success(null);
    }

    @Override
    public Result findQMByYM(String ym) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT scq");
        if(org.apache.commons.lang.StringUtils.isNotBlank(ym)) {
            queryWrapper.eq("ym", ym);
        }
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findZmByQm(String qm) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT zm");
        if(org.apache.commons.lang.StringUtils.isNotBlank(qm)) {
            queryWrapper.eq("scq", qm);
        }
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findJhByzm(String zm) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT ywbh");
        if(org.apache.commons.lang.StringUtils.isNotBlank(zm)) {
            queryWrapper.eq("zm", zm);
        }
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Qj selectOne(String jh) {
        LambdaQueryWrapper<Qj> lambdaQueryWrapper = new LambdaQueryWrapper<Qj>();
        lambdaQueryWrapper.eq(Qj::getYwbh, jh);
        Qj qj = qjMapper.selectOne(lambdaQueryWrapper);
        return  qj;
    }

    @Override
    public List<String> selectJhByScq(List<String> options) {
        LambdaQueryWrapper<Qj> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Qj::getScq,options);
        lambdaQueryWrapper.select(Qj::getYwbh);
        List<Qj> qjs = qjMapper.selectList(lambdaQueryWrapper);
        List<String> lists=new ArrayList<>();
        for(Qj qj:qjs){
            lists.add(qj.getYwbh());
        }
        return lists;
    }

    @Override
    public List<String> selectJhByYm(List<String> options) {
        LambdaQueryWrapper<Qj> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Qj::getYm,options);
        lambdaQueryWrapper.select(Qj::getYwbh);
        List<Qj> qjs = qjMapper.selectList(lambdaQueryWrapper);
        List<String> lists=new ArrayList<>();
        for(Qj qj:qjs){
            lists.add(qj.getYwbh());
        }
        return lists;
    }
    @Override
    public List<String> selectJhByZm(List<String> options) {
        LambdaQueryWrapper<Qj> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Qj::getZm,options);
        lambdaQueryWrapper.select(Qj::getYwbh);
        List<Qj> qjs = qjMapper.selectList(lambdaQueryWrapper);
        List<String> lists=new ArrayList<>();
        for(Qj qj:qjs){
            lists.add(qj.getYwbh());
        }
        return lists;
    }

    @Override
    public Result findZm(int department) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT zm");
        if(department==2){
            List<String> jhList1=qjMapper.findJhByYm("川渝");
            queryWrapper.in("ywbh",jhList1);
        }
        if(department==3){
            List<String> jhList1=qjMapper.findJhByYm("大庆");
            queryWrapper.in("ywbh",jhList1);
        }
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public Result findScq(int department) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT scq");
        if(department==2){
            List<String> jhList1=qjMapper.findJhByYm("川渝");
            queryWrapper.in("ywbh",jhList1);
        }
        if(department==3){
            List<String> jhList1=qjMapper.findJhByYm("大庆");
            queryWrapper.in("ywbh",jhList1);
        }
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }
    @Override
    public Result findJh(int department) {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        if(department==2)
            queryWrapper.eq("ym","川渝");
        if(department==3)
            queryWrapper.eq("ym","大庆");
        queryWrapper.select("DISTINCT ywbh");
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

    @Override
    public List<String> findAllJh() {
        return qjMapper.findAllJh();
    }

    @Override
    public List<String> findJhByYm(String ym) {
        return qjMapper.findJhByYm(ym);
    }

    @Override
    public Result findYm() {
        QueryWrapper<Qj> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT ym");
        List<Map<String, Object>> maps = qjMapper.selectMaps(queryWrapper);
        return Result.success(maps);
    }

}
