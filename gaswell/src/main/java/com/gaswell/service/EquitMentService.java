package com.gaswell.service;

import com.gaswell.pojo.EquipMent;
import com.gaswell.vo.Result;


public interface EquitMentService {
    Result insert( EquipMent equipMent);

    Result delete(int id);

    Result update(EquipMent equipMent);

    Result select(int size, int current, EquipMent equipMent);
}
