package com.gaswell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.Role;
import com.gaswell.vo.Result;

import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public interface RoleService extends IService<Role> {

    boolean save(Role role);

    Result insertOne(Role role);

    Result updateOne(Role role);

//    Result selectByProperties(Role role);

    Result selectAll(int current,int size);

    Result insertBatch(List<Role> list);

    IPage<Role> getPage(int current, int size);

    Result selectByPropertiesPage(Integer current, Integer size, Role role);

    int findByName(String roleName);
}
