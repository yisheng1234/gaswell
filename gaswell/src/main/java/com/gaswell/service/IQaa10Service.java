package com.gaswell.service;

import com.gaswell.entity.Qaa10;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Result;

/**
 * <p>
 * 试气记录 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQaa10Service extends IService<Qaa10> {

    Result insertOne(Qaa10 qaa10);

    Result updateOne(Qaa10 qaa10);

    Result selectProperties( Qaa10 qaa10);

    Result selectAll(int current, int size,String jh);
}
