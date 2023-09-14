package com.gaswell.service.impl;

import com.gaswell.mapper.TgxxMapper;
import com.gaswell.service.TgxxService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TgxxServiceImpl implements TgxxService {
    @Autowired
    private TgxxMapper tgxxMapper;
    @Override
    public Result selectAll() {
        return Result.success(tgxxMapper.selectList(null));
    }
}
