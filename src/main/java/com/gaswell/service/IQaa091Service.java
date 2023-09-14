package com.gaswell.service;

import com.gaswell.entity.Qaa091;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 * 射孔井段数据 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQaa091Service extends IService<Qaa091> {

    Result insertOne(Qaa091 qaa091);

    Result updateOne(Qaa091 qaa091);

    Result selectProperties( Qaa091 qaa091);

    Result selectAll(int current, int size,String jh);
}
