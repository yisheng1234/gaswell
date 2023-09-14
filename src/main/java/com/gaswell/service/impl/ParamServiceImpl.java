package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.ParamMapper;
import com.gaswell.pojo.Param;
import com.gaswell.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:54
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements ParamService {

    @Autowired
    private ParamMapper paramMapper;

    @Override
    public Param selectOneByEntity(Param param) {
        LambdaQueryWrapper<Param> lqw = new LambdaQueryWrapper<Param>();

        lqw.eq(Param::getParam_name,param.getParam_name());
        return paramMapper.selectOne(lqw);
    }
}
