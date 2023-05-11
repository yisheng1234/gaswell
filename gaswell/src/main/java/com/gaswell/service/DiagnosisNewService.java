package com.gaswell.service;

import com.gaswell.pojo.Diagnosis;

import java.util.List;

public interface DiagnosisNewService {
    void dataUnusual();

    void JYPolice();

    void DDPolice();

    void JYFXPolice();

    void clear();

    Integer getDataNum();

    void gj();

    List<Diagnosis> findDataByTime(String jh, String cjsj);
}
