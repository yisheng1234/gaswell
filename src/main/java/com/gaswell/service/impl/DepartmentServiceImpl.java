package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.mapper.DepartmentMapper;
import com.gaswell.pojo.Department;
import com.gaswell.service.DepartmentService;
import com.gaswell.vo.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@DS("mysql")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public Result insertOne(Department department) {
        departmentMapper.insert(department);
        return Result.success(null);
    }

    @Override
    public Result updateOne(Department department) {

        departmentMapper.updateById(department);
        return Result.success(null);
    }

    @Override
    public Object removeById(Integer id) {
        departmentMapper.deleteById(id);
        return null;
    }

    @Override
    public int fineByName(String department) {
        LambdaQueryWrapper<Department> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Department::getDepartment_name,department);
        lambdaQueryWrapper.select(Department::getDepartment_id);
        Department department1 = departmentMapper.selectOne(lambdaQueryWrapper);

        return department1.getDepartment_id();
    }

    @Override
    public Result selectProperties(int current,int size,Department department) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LambdaQueryWrapper<Department> lqw = new LambdaQueryWrapper<Department>();
        lqw.like(department.getDepartment_id()!=0,Department::getDepartment_id, department.getDepartment_id());
        lqw.like(Strings.isNotEmpty(department.getDepartment_name()), Department::getDepartment_name,department.getDepartment_name());
        IPage<Department> page=new Page<>(current,size);
        departmentMapper.selectPage(page, null);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }


    @Override
    public Result selectAll(int current,int size) {
        IPage<Department> page=new Page<>(current,size);
        departmentMapper.selectPage(page, null);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
//
//    @Override
//    public Result insertBatch(List<Permission> list) {
//        permissionMapper.insertBatchSomeColumn(list);
//        return Result.success(null);
//    }

//    @Override
//    public IPage<Department> getPage(int current, int size) {
//        IPage<Department> page = new Page<Department>(current,size);
//        departmentMapper.selectPage(page,null);
//        return page;
//    }

//    @Override
//    public IPage<Department> selectByPropertiesPage(Integer current, Integer size, Permission permission) {
//        LambdaQueryWrapper<Department> lqw = new LambdaQueryWrapper<Permission>();
//        lqw.like(permission.getPermission_id()!=0,Permission::getPermission_id, permission.getPermission_id());
//        lqw.like(Strings.isNotEmpty(permission.getPermission_name()), Permission::getPermission_name,permission.getPermission_name());
//        IPage<Permission> page = new Page<Permission>(current,size);
//        permissionMapper.selectPage(page,lqw);
//        return page;
//    }
}
