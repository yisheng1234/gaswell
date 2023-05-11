package com.gaswell.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParams {
    private String realName;
    private String userName;
    private String passWord;
    private String department;
    private String roleName;
}
