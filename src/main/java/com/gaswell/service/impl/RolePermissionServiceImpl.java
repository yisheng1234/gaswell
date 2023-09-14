package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.PermissionMapper;
import com.gaswell.mapper.RoleMapper;
import com.gaswell.mapper.RolePermissionMapper;
import com.gaswell.pojo.Permission;
import com.gaswell.pojo.Role;
import com.gaswell.pojo.RolePermission;
import com.gaswell.service.RolePermissionService;
import com.gaswell.vo.Result;
import com.gaswell.vo.RoleAndPermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/21/ 14:22
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService{
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired 
    private PermissionMapper permissionMapper;
    @Override
    public Result selectAll(int current, int size) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        if (current > 0) {
            IPage<RolePermission> page=new Page<>(current, size);
            IPage<RolePermission> yscsjIPage = rolePermissionMapper.selectPage(page, null);
            List<RolePermission> yscsjs = yscsjIPage.getRecords();
            RoleAndPermissionVo roleAndPermissionParams=new RoleAndPermissionVo();
            for (RolePermission yscsj : yscsjs) {
                Permission permission=permissionMapper.selectById(yscsj.getPermission_id());
                Role role=roleMapper.selectById(yscsj.getRole_id());

                BeanUtils.copyProperties(permission,roleAndPermissionParams);
                BeanUtils.copyProperties(role,roleAndPermissionParams);
            }

            return Result.success(roleAndPermissionParams);
        }
        return Result.success(rolePermissionMapper.selectList(null));
    }

//    @Override
//    public Result addPermissionForRole(int roleId, String permissionName) {
//        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("permission_name",permissionName);
//        Permission permission = permissionMapper.selectOne(queryWrapper);
//        if(permission!=null) {
//            RolePermission rolePermission = new RolePermission();
//            rolePermission.setPermission_id(permission.getPermission_id());
//            rolePermission.setRole_id(roleId);
//            rolePermissionMapper.insert(rolePermission);
//            return Result.success(null);
//        }
//        return Result.fail(111,"无此权限");
//    }
    @Override
    public Result addPermissionForRole(int roleId, ArrayList<Integer> permissions) {

        RolePermission rolePermission=new RolePermission();
        rolePermission.setRole_id(roleId);
        for (Integer permission : permissions) {
            rolePermission.setPermission_id(permission);
            LambdaQueryWrapper<RolePermission> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RolePermission::getRole_id,roleId);
            lambdaQueryWrapper.eq(RolePermission::getPermission_id,permission);
            RolePermission rolePermission1 = rolePermissionMapper.selectOne(lambdaQueryWrapper);
            if(rolePermission1==null) {
                rolePermissionMapper.insert(rolePermission);
            }
        }
        return Result.success(null);
    }
    @Override
    public Result deletePermissionForRole(int roleId, int permissionId) {
//        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("permission_name",permissionId);
//        Permission permission = permissionMapper.selectOne(queryWrapper);
//        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(RolePermission::getRole_id,roleId);
//        lambdaQueryWrapper.eq(RolePermission::getPermission_id,permission.getPermission_id());
//        rolePermissionMapper.delete(lambdaQueryWrapper);
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermission::getPermission_id,permissionId);
        lambdaQueryWrapper.eq(RolePermission::getRole_id,roleId);
        rolePermissionMapper.delete(lambdaQueryWrapper);
        return Result.success(null);
    }


}
