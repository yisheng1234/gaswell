package com.gaswell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionVo {
    private Integer permission_id;
    private String permission_name;
    private String path;
    private Integer father;
    private Integer type;
    private String icon;
    private List<PermissionVo> children;
}
