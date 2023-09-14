package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Rscsj;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
public interface RscsjService extends IService<Rscsj> {
   Result insertOne(Rscsj rscsj,String ywjh);

   Result updateOne(Rscsj rscsj,String ywjh);

   Result selectProperties(int current,int size,Rscsj rscsj,String ywjh) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

   Result selectAll(int current,int size,String ywjh);

   Result insertBatch(List<Rscsj> list);

   IPage<Rscsj> getPage(int current, int size);

   IPage<Rscsj> selectByPropertiesPage(Integer current, Integer size, Rscsj rscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

   IPage<Rscsj> getPageByYwjh(int current, int size, String ywjh);

   Result selectAllByYwjh(int current,int size, String ywjh);

   Result showdata(String ywjh, String date, String properties);

    Result getColumnNameAndComment(String ywjh);
}
