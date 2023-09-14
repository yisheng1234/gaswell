package com.gaswell.service;

import com.gaswell.pojo.Department;
import com.gaswell.vo.Result;

import java.lang.reflect.InvocationTargetException;

public interface DepartmentService {
    Result selectAll(int current, int size);

    Result selectProperties(int current, int size, Department department) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    Result insertOne(Department department);

    Result updateOne(Department department);

    Object removeById(Integer id);

    int  fineByName(String department);
}
