package com.gaswell.service;

import com.gaswell.entity.Qfc02;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQfc02Service extends IService<Qfc02> {

    Result insertOne(Qfc02 qfc02);

    Result updateOne(Qfc02 qfc02);

    Result selectProperties( Qfc02 qfc02);

    Result selectAll(int current, int size,String jh);
}
