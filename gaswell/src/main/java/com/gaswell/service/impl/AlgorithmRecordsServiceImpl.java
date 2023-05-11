package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.AlgorithmRecordsMapper;
import com.gaswell.pojo.AlgorithmRecords;
import com.gaswell.service.AlgorithmRecordsService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.AlgorithmRecordsParams;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("mysql")
public class AlgorithmRecordsServiceImpl extends ServiceImpl<AlgorithmRecordsMapper, AlgorithmRecords> implements AlgorithmRecordsService {
    @Autowired
    private AlgorithmRecordsMapper algorithmRecordsMapper;
    @Override
    public Result selectByProperties(AlgorithmRecords algorithmRecords) {
        LambdaQueryWrapper<AlgorithmRecords> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(algorithmRecords.getRecord_id()!=null && algorithmRecords.getRecord_id()!=0, AlgorithmRecords::getRecord_id,algorithmRecords.getRecord_id());
        queryWrapper.eq(algorithmRecords.getUser_id()!=null && algorithmRecords.getUser_id()!=0, AlgorithmRecords::getUser_id,algorithmRecords.getUser_id());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getAlgorithm_type()),AlgorithmRecords::getAlgorithm_type,algorithmRecords.getAlgorithm_type());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getAlgorithm_name()),AlgorithmRecords::getAlgorithm_name,algorithmRecords.getAlgorithm_name());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getDatabase_condition_type()), AlgorithmRecords::getDatabase_condition_type,algorithmRecords.getDatabase_condition_type());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getDatabase_condition()), AlgorithmRecords::getDatabase_condition,algorithmRecords.getDatabase_condition());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getDatabase_type()),AlgorithmRecords::getDatabase_type,algorithmRecords.getDatabase_type());
        List<AlgorithmRecords> algorithmRecord = algorithmRecordsMapper.selectList(queryWrapper);
        return Result.success(algorithmRecord);
    }

    @Override
    public Result deleteByProperties(AlgorithmRecordsParams algorithmRecordsParams) {
        LambdaQueryWrapper<AlgorithmRecords> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(algorithmRecordsParams.getRecord_id()!=null && algorithmRecordsParams.getRecord_id()!=0, AlgorithmRecords::getRecord_id,algorithmRecordsParams.getRecord_id());
        queryWrapper.eq(algorithmRecordsParams.getUser_id()!=null && algorithmRecordsParams.getUser_id()!=0,AlgorithmRecords::getUser_id,algorithmRecordsParams.getUser_id());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecordsParams.getAlgorithm_type()), AlgorithmRecords::getAlgorithm_type,algorithmRecordsParams.getAlgorithm_type());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecordsParams.getAlgorithm_name()),AlgorithmRecords::getAlgorithm_name,algorithmRecordsParams.getAlgorithm_name());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecordsParams.getDatabase_condition_type()),AlgorithmRecords::getDatabase_condition_type,algorithmRecordsParams.getDatabase_condition_type());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecordsParams.getDatabase_condition()), AlgorithmRecords::getDatabase_condition,algorithmRecordsParams.getDatabase_condition());
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecordsParams.getDatabase_type()), AlgorithmRecords::getDatabase_type,algorithmRecordsParams.getDatabase_type());
        int result = algorithmRecordsMapper.delete(queryWrapper);
        return Result.success(result);
    }

    @Override
    public Result selectByTypeHightest(AlgorithmRecords algorithmRecords) {
        LambdaQueryWrapper<AlgorithmRecords> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(algorithmRecords.getAlgorithm_type()),AlgorithmRecords::getAlgorithm_type,algorithmRecords.getAlgorithm_type());
        queryWrapper.orderByDesc(AlgorithmRecords::getTestGrade);
        queryWrapper.last("limit 1");
        AlgorithmRecords algorithmRecord = algorithmRecordsMapper.selectOne(queryWrapper);
        return Result.success(algorithmRecord);
    }

    @Override
    public Result updateUsedAlgorithm(String algorithm_type, String algorithm_name) {
        AlgorithmRecords algorithmRecords=new AlgorithmRecords();
        algorithmRecords.setIfUse("2");
//        先将之前  设置为使用状态的算法改为  “2”
        LambdaUpdateWrapper<AlgorithmRecords> queryWrapper=new LambdaUpdateWrapper<>();
        queryWrapper.eq(AlgorithmRecords::getAlgorithm_type,algorithm_type);
        queryWrapper.eq(AlgorithmRecords::getIfUse,"1");
        algorithmRecordsMapper.update(algorithmRecords,queryWrapper);

        LambdaUpdateWrapper<AlgorithmRecords> queryWrapper1=new LambdaUpdateWrapper<>();
        queryWrapper1.eq(AlgorithmRecords::getAlgorithm_name,algorithm_name);
        queryWrapper1.eq(AlgorithmRecords::getAlgorithm_type,algorithm_type);
        algorithmRecords.setIfUse("1");
        algorithmRecordsMapper.update(algorithmRecords,queryWrapper1);
        return Result.success(null);
    }
}
