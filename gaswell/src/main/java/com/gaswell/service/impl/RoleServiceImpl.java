package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.gaswell.service.RoleService;
import com.gaswell.vo.Result;
import com.gaswell.vo.RoleAndPermissionVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:43
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;



    @Override
    public boolean save(Role role) {
        List<Role> roleList = roleMapper.selectList(null);
        boolean flag = true;
        for (Role role1 : roleList) {
            if(role1.getRole_name().equals(role.getRole_name()) || role1.getRole_type()==role.getRole_type()){
                flag=false;
            }
        }
        if(flag==true){
            roleMapper.insert(role);
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public Result selectAll(int current, int size) {
        IPage<Role> page=new Page<Role>(current,size);
        IPage<Role> roleIPage = roleMapper.selectPage(page, null);
        List<Role> records = roleIPage.getRecords();
        List<RoleAndPermissionVo> list=findPermission(records);
        return Result.success(list);
    }

    @Override
    public Result selectByPropertiesPage(Integer current, Integer size, Role role) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.like(role.getRole_id()!=0,Role::getRole_id, role.getRole_id());
        lqw.like(Strings.isNotEmpty(role.getRole_name()), Role::getRole_name,role.getRole_name());
        lqw.like(Strings.isNotEmpty(role.getRole_remarks()), Role::getRole_remarks, role.getRole_remarks());
        lqw.in(Strings.isNotEmpty(role.getRole_type()) ,Role::getRole_type, role.getRole_type());
        IPage<Role> page = new Page<Role>(current,size);
        IPage<Role> roleIPage = roleMapper.selectPage(page, lqw);
        List<Role> records = roleIPage.getRecords();
        List<RoleAndPermissionVo> list=findPermission(records);
        return Result.success(list);
    }

    @Override
    public int findByName(String roleName) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getRole_name,roleName);
        Role role = roleMapper.selectOne(lambdaQueryWrapper);
        return role.getRole_id();
    }

    public List<RoleAndPermissionVo>  findPermission(List<Role> records) {
        List<RoleAndPermissionVo> list=new ArrayList<>();
        for (Role record : records) {
            RoleAndPermissionVo roleAndPermissionParams=new RoleAndPermissionVo();
            BeanUtils.copyProperties(record,roleAndPermissionParams);
            int roleId=record.getRole_id();
            LambdaQueryWrapper<RolePermission> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RolePermission::getRole_id,roleId);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(lambdaQueryWrapper);
            List<Integer> permissionIdList=new ArrayList<>();
            for (RolePermission rolePermission : rolePermissions) {
                permissionIdList.add(rolePermission.getPermission_id());
            }
            if(permissionIdList.size()!=0) {
                QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("permission_id", permissionIdList);
                List<Permission> permissions = permissionMapper.selectList(queryWrapper);
                List<String> permission_names = new ArrayList<>();
                for (Permission permission : permissions) {
                    permission_names.add(permission.getPermission_name());
                }
                roleAndPermissionParams.setPermission_names(permission_names);
            }
            list.add(roleAndPermissionParams);
        }
        return list;
    }
    @Override
    public Result insertOne(Role role) {
        roleMapper.insert(role);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Role role) {

        roleMapper.updateById(role);
        return Result.success(null);
    }


    @Override
    public Result insertBatch(List<Role> list) {
        roleMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public IPage<Role> getPage(int current, int size) {
        IPage<Role> page = new Page<Role>(current,size);
        roleMapper.selectPage(page,null);
        return page;
    }
}
