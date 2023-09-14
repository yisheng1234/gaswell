package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.RolePermission;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author Lei Wang
 * @Date: 2021/12/21/ 14:21
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface RolePermissionService extends IService<RolePermission> {
    public Result selectAll(int current,int size) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Result addPermissionForRole(int roleId, ArrayList<Integer> permissions);

    Result deletePermissionForRole(int roleId,int permissionId);
}
