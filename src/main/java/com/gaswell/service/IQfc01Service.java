package com.gaswell.service;

import com.gaswell.entity.Qfc01;
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
public interface IQfc01Service extends IService<Qfc01> {

    Result insertOne(Qfc01 qfc01);

    Result updateOne(Qfc01 qfc01);

    Result selectProperties( Qfc01 qfc01);

    Result selectAll(int current, int size,String jh);
}
