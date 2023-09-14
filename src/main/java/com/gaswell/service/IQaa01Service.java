package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.entity.Qaa01;
import com.gaswell.vo.Result;

import java.util.List;

/**
 * <p>
 * 气井基础信息 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQaa01Service extends IService<Qaa01> {

    List<String> selectJh();

    Result insertOne(Qaa01 qaa01);

    Result updateOne(Qaa01 qaa01);

    Result selectProperties( Qaa01 qaa01);

    Result selectAll(int current, int size,String jh,int department);


    Integer dataCount();

    Result selectJh(String qm,String zm);

    Result selectQM();

    Result selectZM(String qm);

    Qaa01 selectOne(String jh);
}
