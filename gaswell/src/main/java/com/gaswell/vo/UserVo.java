package com.gaswell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Integer user_id;
    private String user_name;
    private String user_realname;
    private String user_password;
    private String user_role_name;
    private String user_department_name;
    private List<PermissionVo> permissionList;
    private String user_remarks;
}
