package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Yscsj;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:42
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface YscsjService extends IService<Yscsj> {
    Result insertOne(Yscsj yscsj,String ywjh);

    Result updateOne(Yscsj yscsj,String ywjh);

    Result selectProperties(int current,int size,Yscsj yscsj,String ywjh) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size,String ywjh);

    Result selectAllByYwjh(int current,int size, String ywjh);

    void insertBatch(List<Yscsj> list);

    IPage<Yscsj> selectByPropertiesPage(Integer current, Integer size, Yscsj yscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result showdata(String ywjh, String date, String properties);

    Result getColumnNameAndComment(String ywjh);
}
