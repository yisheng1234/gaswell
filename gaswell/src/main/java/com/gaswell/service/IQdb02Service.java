package com.gaswell.service;

import com.gaswell.entity.Qdb02;
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
public interface IQdb02Service extends IService<Qdb02> {

    Result insertOne(Qdb02 qdb02);

    Result updateOne(Qdb02 qdb02);

    Result selectProperties( Qdb02 qdb02);

    Result selectAll(int current, int size,String jh);
}
