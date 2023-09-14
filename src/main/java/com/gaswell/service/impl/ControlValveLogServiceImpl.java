package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.ControlValveLogMapper;
import com.gaswell.mapper.YscsjMapper;
import com.gaswell.pojo.ControlValveLog;
import com.gaswell.pojo.Yscsj;
import com.gaswell.service.ControlValveLogService;
import org.springframework.stereotype.Service;

/**
 * @author Lei Wang
 * @Date: 2022/03/04/ 16:35
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Service
@DS("mysql")
public class ControlValveLogServiceImpl extends ServiceImpl<ControlValveLogMapper, ControlValveLog> implements ControlValveLogService {
}
