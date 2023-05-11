package com.gaswell.service;

import com.gaswell.entity.Qca02;
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
public interface IQca02Service extends IService<Qca02> {

    Result insertOne(Qca02 qca02);

    Result updateOne(Qca02 qca02);

    Result selectProperties( Qca02 qca02);

    Result selectAll(int current, int size,String jh);
}
