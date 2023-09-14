package com.gaswell.mapper;

import com.gaswell.pojo.Permission;
import com.gaswell.pojo.RolePermission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/21/ 14:20
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Repository
public interface RolePermissionMapper extends EasyBaseMapper<RolePermission>{
    @Select("select permission_name FROM permission where permission_id in(select permission_id from rolepermission where role_id=${roleId})")
    List<String> selectPermissionNameByRoleId(Integer roleId);
    @Select("select * FROM permission where permission_id in(select permission_id from rolepermission where role_id=${roleId})")
    List<Permission> selectPermisssionByRoleId(Integer roleId);
}
