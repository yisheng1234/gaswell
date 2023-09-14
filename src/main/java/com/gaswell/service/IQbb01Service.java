package com.gaswell.service;

import com.gaswell.entity.Qbb01;
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
public interface IQbb01Service extends IService<Qbb01> {

    Result insertOne(Qbb01 qbb01);

    Result updateOne(Qbb01 qbb01);

    Result selectProperties( Qbb01 qbb01);

    Result selectAll(int current, int size,String jh);
}
