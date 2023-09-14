package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Permission;
import com.gaswell.vo.PermissionVo;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface PermissionService extends IService<Permission> {

    Result insertOne(Permission permission);

    Result updateOne(Permission permission);

    Result selectProperties(int current,int size,Permission permission) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result selectAll(int current,int size);

    Result insertBatch(List<Permission> list);

    IPage<Permission> getPage(int current, int size);

    IPage<Permission> selectByPropertiesPage(Integer current, Integer size, Permission permission);

    List<PermissionVo> findPermissionVo(Integer roleId);
}
