package com.gaswell.service;

import com.gaswell.entity.Qaa02;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 * 钻井地质信息 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQaa02Service extends IService<Qaa02> {

    Result insertOne(Qaa02 qaa02);

    Result updateOne(Qaa02 qaa02);

    Result selectProperties( Qaa02 qaa02);

    Result selectAll(int current, int size,String jh);
}
