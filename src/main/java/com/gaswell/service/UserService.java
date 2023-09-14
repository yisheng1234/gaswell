package com.gaswell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.pojo.User;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.LoginParams;

import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

public interface UserService extends IService<User> {
    List<User> selectByProperties(User user);

    Boolean removeByRole(Integer id);

    Boolean removeByDepartment(Integer id);

    Result selectByPropertiesPage(Integer current, Integer size, User user);

    Result selectAll(int current, int size);

    User selectByNameAndPwd(LoginParams loginParams);

    User selectUserByuserName(String userName);

    Result selectPermissionByuserName();

    Result getCurrentUser(String token);

    Result updateUser(User user);


    // 更改用户角色


}
