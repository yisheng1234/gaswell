package com.gaswell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndPermissionVo {
    private Integer role_id;
    private String role_type;
    private String role_name;
    private String role_remarks;
    private List<String> permission_names;
}
