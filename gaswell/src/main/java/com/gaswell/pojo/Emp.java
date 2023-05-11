package com.gaswell.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Lei Wang
 * @Date: 2022/04/15/ 16:11
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@ToString
@TableName("emp")
public class Emp {
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hiredate;
    private double sal;
    private double comm;
    private Integer deptno;
}
