package com.gaswell.service;

import com.gaswell.entity.Qba02;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Qba01Vo;
import com.gaswell.vo.Result;

/**
 * <p>
 * 采气井月数据 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQba02Service extends IService<Qba02> {

    Result insertOne(Qba02 qba02);

    Result updateOne(Qba02 qba02);

    Result selectProperties( Qba02 qba02,int sort,int department);

    Result selectAll(int current, int size,String jh,int sort);

    Result selectByMutiProperties(Qba01Vo qba01Vo,String date,int sort,int department);

    Result findZyqm();

    Result findZm();

    Result findDm();

    Result findQkmc();
}
