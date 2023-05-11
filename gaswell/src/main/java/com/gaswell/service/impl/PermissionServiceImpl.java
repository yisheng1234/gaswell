package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.PermissionMapper;
import com.gaswell.mapper.RolePermissionMapper;
import com.gaswell.pojo.Permission;
import com.gaswell.service.PermissionService;
import com.gaswell.vo.PermissionVo;
import com.gaswell.vo.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:42
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public Result insertOne(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Permission permission) {

        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result selectProperties(int current,int size,Permission permission) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LambdaQueryWrapper<Permission> lqw = new LambdaQueryWrapper<Permission>();
        lqw.like(permission.getPermission_id()!=0,Permission::getPermission_id, permission.getPermission_id());
        lqw.like(Strings.isNotEmpty(permission.getPermission_name()), Permission::getPermission_name,permission.getPermission_name());
        IPage<Permission> page=new Page<>(current,size);
        IPage<Permission> page1 = permissionMapper.selectPage(page, lqw);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }



    @Override
    public Result selectAll(int current,int size) {
        IPage<Permission> page=new Page<>(current,size);
        IPage<Permission> page1 = permissionMapper.selectPage(page, null);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }

    @Override
    public Result insertBatch(List<Permission> list) {
        permissionMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public IPage<Permission> getPage(int current, int size) {
        IPage<Permission> page = new Page<Permission>(current,size);
        permissionMapper.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<Permission> selectByPropertiesPage(Integer current, Integer size, Permission permission) {
        LambdaQueryWrapper<Permission> lqw = new LambdaQueryWrapper<Permission>();
        lqw.like(permission.getPermission_id()!=0,Permission::getPermission_id, permission.getPermission_id());
        lqw.like(Strings.isNotEmpty(permission.getPermission_name()), Permission::getPermission_name,permission.getPermission_name());
        IPage<Permission> page = new Page<Permission>(current,size);
        permissionMapper.selectPage(page,lqw);
        return page;
    }

    @Override
    public List<PermissionVo> findPermissionVo(Integer roleId) {
        List<Permission> permissions = rolePermissionMapper.selectPermisssionByRoleId(roleId);
        return  convertList(permissions);
    }
    public List<PermissionVo> findChildren(Permission permission,List<Permission> permissions){

        List<PermissionVo> childPermissionVos=new ArrayList<>();
        for (Permission permission1 : permissions) {
            if(permission.getPermission_id() == permission1.getFather()){
                PermissionVo childPermissionVo=new PermissionVo();
                BeanUtils.copyProperties(permission1,childPermissionVo);
                List<PermissionVo> grandChild= findChildren(permission1,permissions);
                childPermissionVo.setChildren(grandChild);
                childPermissionVos.add(childPermissionVo);
            }
        }
        return childPermissionVos;
    }
    public List<PermissionVo> convertList(List<Permission> permissions){
        List<PermissionVo> permissionVos=new ArrayList<>();
        for (Permission permission : permissions) {
            if(permission.getType()==0){
                PermissionVo permissionVo=new PermissionVo();
                BeanUtils.copyProperties(permission,permissionVo);
                List<PermissionVo> children=findChildren(permission,permissions);
                permissionVo.setChildren(children);
                permissionVos.add(permissionVo);
            }

        }
        return permissionVos;
    }
}
