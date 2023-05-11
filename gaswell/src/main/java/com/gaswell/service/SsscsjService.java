package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Ssscsj;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/10/ 14:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface SsscsjService extends IService<Ssscsj> {

    Result insertOne(Ssscsj ssscsj);

    Result updateOne(Ssscsj ssscsj);

    Result selectProperties(int current,int size,Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size);

    Result insertBatch(List<Ssscsj> list);

    IPage<Ssscsj> getPage(int current, int size);

    IPage<Ssscsj> selectByPropertiesPage(Integer current, Integer size, Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
