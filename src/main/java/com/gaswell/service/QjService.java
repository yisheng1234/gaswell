package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Qj;
import com.gaswell.vo.Result;

import java.util.List;

public interface QjService extends IService<Qj> {
    Result insertOne(Qj qj);

    Result deleteOne(String ywbh);

    Result updateOne(Qj qj);

    Result findAll(int current,int size,int department);

    Result selectByProperties(int current, int size, Qj qj);

    void insertBatch(List<Qj> list);

    Result selectByTxbh(String txbh);

    String findByTxbh(String txbh);

    List<Qj> selectAll();

    Qj findQjByTxbh(String txbh);

    Result readJhFromOracle();

    Result findYm();

    Result findQMByYM(String ym);

    Result findZmByQm(String qm);

    Result findJhByzm(String zm);

    Qj selectOne(String jh);

    List<String> selectJhByScq(List<String> options);

    List<String> selectJhByYm(List<String> options);
    List<String> selectJhByZm(List<String> options);

    Result findZm(int department);

    Result findScq(int department);

    Result findJh(int department);

    List<String> findAllJh();

    List<String> findJhByYm(String ym);
}
