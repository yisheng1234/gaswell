package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.EmpMapper;
import com.gaswell.pojo.Emp;
import com.gaswell.service.EmpService;
import org.springframework.stereotype.Service;

/**
 * @author Lei Wang
 * @Date: 2022/04/15/ 16:23
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("oracle")
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

}
