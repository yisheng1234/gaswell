package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.ParamsMapper;
import com.gaswell.mapper.ParamsUpdateRecordMapper;
import com.gaswell.pojo.Params;
import com.gaswell.pojo.ParamsUpdateRecord;
import com.gaswell.service.ParamsService;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:54
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class ParamsServiceImpl extends ServiceImpl<ParamsMapper, Params> implements ParamsService {

    @Autowired
    private ParamsMapper paramsMapper;
    @Autowired
    private ParamsUpdateRecordMapper paramsUpdateRecordMapper;

    @Override
    public Params selectOne(Params params) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<Params>();
        lqw.eq(Params::getParam_name,params.getParam_name());
        return paramsMapper.selectOne(lqw);
    }

    @Override
    public List<Params> selectList(Params params) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<Params>();
        lqw.like(params.getParam_name() != null && Strings.isNotEmpty(params.getParam_name()), Params::getParam_name,params.getParam_name());
        lqw.eq(Params::getParam_jh,params.getParam_jh());
        return paramsMapper.selectList(lqw);
    }

    @Override
    public int insertOne(Params params) {
        return paramsMapper.insert(params);
    }

    @Override
    public int delete(Params params) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<Params>();
        lqw.like(params.getParam_name() != null && Strings.isNotEmpty(params.getParam_name()), Params::getParam_name,params.getParam_name());
        lqw.eq(params.getParam_jh() != null && Strings.isNotEmpty(params.getParam_jh()), Params::getParam_jh,params.getParam_jh());
        return paramsMapper.delete(lqw);
    }

    @Override
    public int update(Params params) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<Params>();
        lqw.like(params.getParam_name() != null && Strings.isNotEmpty(params.getParam_name()), Params::getParam_name,params.getParam_name());
        lqw.eq(params.getParam_jh() != null && Strings.isNotEmpty(params.getParam_jh()), Params::getParam_jh,params.getParam_jh());
        return paramsMapper.update(params, lqw);
    }

    @Override
    public Result updateOne(Params params, String userName) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<Params>();
        lqw.eq(params.getParam_name() != null && Strings.isNotEmpty(params.getParam_name()), Params::getParam_name,params.getParam_name());
        lqw.eq(params.getParam_jh() != null && Strings.isNotEmpty(params.getParam_jh()), Params::getParam_jh,params.getParam_jh());
        paramsMapper.update(params, lqw);
        ParamsUpdateRecord paramsUpdateRecord=new ParamsUpdateRecord();
        paramsUpdateRecord.setUserName(userName);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        paramsUpdateRecord.setTime(sdf.format(new Date()));
        StringBuilder sb=new StringBuilder();
        if(StringUtils.isNotBlank(params.getParam_jh())) {
            sb.append("修改");
            sb.append(params.getParam_jh());
            sb.append("的");
        }
        if(StringUtils.isNotBlank(params.getParam_name())){
            sb.append(params.getParam_name()+"参数为");
        }
        sb.append(params.getParam_value());
        paramsUpdateRecord.setContent(sb.toString());
        paramsUpdateRecordMapper.insert(paramsUpdateRecord);
        return Result.success(null);
    }
}
