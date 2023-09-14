package com.gaswell.service;

import com.gaswell.entity.Qca03;
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
public interface IQca03Service extends IService<Qca03> {

    Result insertOne(Qca03 qca03);

    Result updateOne(Qca03 qca03);

    Result selectProperties( Qca03 qca03);

    Result selectAll(int current, int size,String jh);
}
