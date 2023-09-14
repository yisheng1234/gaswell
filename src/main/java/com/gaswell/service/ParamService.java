package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Param;
import com.gaswell.pojo.Permission;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:53
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface ParamService extends IService<Param> {

    Param selectOneByEntity(Param param);
}
