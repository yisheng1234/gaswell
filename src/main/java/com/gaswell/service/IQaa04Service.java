package com.gaswell.service;

import com.gaswell.entity.Qaa04;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 * 套管记录 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQaa04Service extends IService<Qaa04> {

    Result insertOne(Qaa04 qaa04);

    Result updateOne(Qaa04 qaa04);

    Result selectProperties( Qaa04 qaa04);

    Result selectAll(int current, int size,String jh);
}
