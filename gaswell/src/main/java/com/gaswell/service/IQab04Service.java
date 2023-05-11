package com.gaswell.service;

import com.gaswell.entity.Qab04;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 * 气藏流体性质数据 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQab04Service extends IService<Qab04> {

    Result insertOne(Qab04 qab04);

    Result updateOne(Qab04 qab04);

    Result selectProperties( Qab04 qab04);

    Result selectAll(int current, int size,String jh);
}
