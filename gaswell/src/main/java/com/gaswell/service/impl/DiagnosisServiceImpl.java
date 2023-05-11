package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.mapper.DiagnosisMapper;
import com.gaswell.mapper.DiagnosisNewMapper;
import com.gaswell.pojo.Diagnosis;
import com.gaswell.service.DiagnosisService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@DS("mysql")
public class DiagnosisServiceImpl implements DiagnosisService {
    @Autowired
    private DiagnosisMapper diagnosisMapper;
    @Autowired
    private DiagnosisNewMapper diagnosisNewMapper;
    @Override
    public Result findLatestData() {
        List<Diagnosis> list=new ArrayList<>();
        List<String> jhList=diagnosisMapper.selectAllJh();
        for (String jh : jhList) {
            Diagnosis diagnosis =diagnosisMapper.findLatestData(jh);
            list.add(diagnosis);
        }

        return  Result.success(list);
    }


}
