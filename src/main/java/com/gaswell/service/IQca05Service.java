package com.gaswell.service;

import com.gaswell.entity.Qca05;
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
public interface IQca05Service extends IService<Qca05> {

    Result insertOne(Qca05 qca05);

    Result updateOne(Qca05 qca05);

    Result selectProperties( Qca05 qca05);

    Result selectAll(int current, int size,String jh);
}
