package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.DepartmentMapper;
import com.gaswell.mapper.RoleMapper;
import com.gaswell.mapper.RolePermissionMapper;
import com.gaswell.mapper.UserMapper;
import com.gaswell.pojo.Department;
import com.gaswell.pojo.Role;
import com.gaswell.pojo.User;
import com.gaswell.service.LoginService;
import com.gaswell.service.PermissionService;
import com.gaswell.service.UserService;
import com.gaswell.utils.DESUtils;
import com.gaswell.vo.PermissionVo;
import com.gaswell.vo.Result;
import com.gaswell.vo.UserVo;
import com.gaswell.vo.params.LoginParams;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User user;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private LoginService loginService;

    @Override

    public List<User> selectByProperties(User user) {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(user.getUser_id()!=0,User::getUser_id,user.getUser_id());
        lqw.like(user.getUser_role_id()!=0,User::getUser_role_id,user.getUser_role_id());
        lqw.like(user.getUser_department_id()!=0,User::getUser_department_id,user.getUser_department_id());
        lqw.like(Strings.isNotEmpty(user.getUser_name()),User::getUser_name,user.getUser_name());
        lqw.like(Strings.isNotEmpty(user.getUser_password()),User::getUser_password,user.getUser_password());
        lqw.like(Strings.isNotEmpty(user.getUser_realname()),User::getUser_realname,user.getUser_realname());
        lqw.like(Strings.isNotEmpty(user.getUser_remarks()),User::getUser_remarks,user.getUser_remarks());
        List<User> userList = userMapper.selectList(lqw);
        return userList;
    }

    @Override

    public Result selectByPropertiesPage(Integer current, Integer size, User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(user.getUser_id()!=0,User::getUser_id,user.getUser_id());
        lqw.like(user.getUser_role_id()!=0,User::getUser_role_id,user.getUser_role_id());
        lqw.like(user.getUser_department_id()!=0,User::getUser_department_id,user.getUser_department_id());
        lqw.like(Strings.isNotEmpty(user.getUser_name()),User::getUser_name,user.getUser_name());
        lqw.like(Strings.isNotEmpty(user.getUser_password()),User::getUser_password,user.getUser_password());
        lqw.like(Strings.isNotEmpty(user.getUser_realname()),User::getUser_realname,user.getUser_realname());
        lqw.like(Strings.isNotEmpty(user.getUser_remarks()),User::getUser_remarks,user.getUser_remarks());

        IPage<User> page = new Page<User>(current,size);
        userMapper.selectPage(page, lqw);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }

    @Override

    public Result selectAll(int current, int size) {

        IPage<User> page=new Page<>(current,size);
        userMapper.selectPage(page,null);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
//        List<User> records = userIPage.getRecords();
//        return Result.success(convertList(records));
    }

    @Override

    public User selectByNameAndPwd(LoginParams loginParams) {
        System.out.println(loginParams);
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUser_name,loginParams.getUserName());
        lambdaQueryWrapper.eq(User::getUser_password,loginParams.getPassword());
        return userMapper.selectOne(lambdaQueryWrapper);

    }

    @Override
    @DS("mysql")
    public User selectUserByuserName(String userName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUser_name,userName);
        lambdaQueryWrapper.last("limit 1");
        User user = userMapper.selectOne(lambdaQueryWrapper);
        return user;
    }

    @Override

    public Result selectPermissionByuserName() {
        return null;
    }

    @Override

    public Result getCurrentUser(String token) {
        User sysUser=loginService.checkToken(token);

        if(sysUser==null){
            return Result.fail(111, "token错误或失效");
        }
        sysUser.setUser_password(DESUtils.decryptBasedDes(sysUser.getUser_password()));
        return Result.success(convert(sysUser));
    }
    private static final String slat="gaswell";
    @Override
    public Result updateUser(User user) {
        if(StringUtils.isNotEmpty(user.getUser_password())) {
            String pwd = user.getUser_password();
            pwd= DESUtils.encryptBasedDes(pwd);
//            pwd = DigestUtils.md5Hex(pwd + slat);
            user.setUser_password(pwd);
        }
        userMapper.updateById(user);
        return Result.success(null);
    }

    public List<UserVo> convertList(List<User> users){
        List<UserVo> userVos=new ArrayList<>();
        for (User user1 : users) {
            UserVo userVo = convert(user1);
            userVos.add(userVo);
        }
        return userVos;
    }

    public UserVo convert(User user){
        //最终返回userVo
        UserVo userVo=new UserVo();
        List<PermissionVo> permissionVos=new ArrayList<>();
        BeanUtils.copyProperties(user,userVo);

        Department department = departmentMapper.selectById(user.getUser_department_id());

        Role role = roleMapper.selectById(user.getUser_role_id());
        userVo.setUser_department_name(department.getDepartment_name());
        userVo.setUser_role_name(role.getRole_name());

        permissionVos=permissionService.findPermissionVo(user.getUser_role_id());
        userVo.setPermissionList(permissionVos);
        return userVo;
    }

    @Override

    public Boolean removeByRole(Integer id) {
        user.setUser_role_id(id);
        user.setUser_id(0);
        user.setUser_department_id(0);
        List<User> userList = userService.selectByProperties(user);
        for (User user1 : userList) {
            userMapper.deleteById(user1.getUser_id());
        }
        return true;
    }

    @Override

    public Boolean removeByDepartment(Integer id) {
        user.setUser_role_id(0);
        user.setUser_id(0);
        user.setUser_department_id(id);
        List<User> userList = userService.selectByProperties(user);
        for (User user1 : userList) {
            userMapper.deleteById(user1.getUser_id());
        }
        return true;
    }


}
