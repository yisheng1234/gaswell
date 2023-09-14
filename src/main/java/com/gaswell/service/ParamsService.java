package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Params;
import com.gaswell.vo.Result;

import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:53
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface ParamsService extends IService<Params> {

    Params selectOne(Params params);

    List<Params> selectList(Params params);

    int insertOne(Params params);

    int delete(Params params);

    int update(Params params);

    Result updateOne(Params params, String userName);
}
