package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.SystemLogMapper;
import com.gaswell.pojo.SystemLog;
import com.gaswell.service.SystemLogService;
import com.gaswell.vo.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@DS("mysql")
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog>  implements SystemLogService {
    @Autowired
    private SystemLogMapper systemLogMapper;


    @Override
    public void insertOne(SystemLog systemLog) {
        systemLogMapper.insert(systemLog);
    }

    @Override
    public Result deleteOne(SystemLog systemLog) {
        int result = systemLogMapper.deleteById(systemLog.getLog_id());
        if(result == 1){
            return Result.success(null);
        }
        else {
           return Result.fail(0,"删除失败");
        }
    }

    @Override
    public Result updateOne(SystemLog systemLog) {
        systemLogMapper.updateById(systemLog);
        return Result.success(null);
    }

    @Override
    public Result selectByProperties(SystemLog systemLog,int current,int size) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        LambdaQueryWrapper<SystemLog> lqw = new LambdaQueryWrapper<SystemLog>();
        lqw.like(systemLog.getLog_id()!=0, SystemLog::getLog_id, systemLog.getLog_id());
        lqw.like(Strings.isNotEmpty(systemLog.getOperate_realname()), SystemLog::getOperate_realname,systemLog.getOperate_realname());

        IPage<SystemLog> page=new Page<>(current,size);
        IPage<SystemLog> page1 = systemLogMapper.selectPage(page, lqw);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());

    }



    @Override
    public Result selectAll(int current,int size) {
        if(current==-1){
            List<SystemLog> list = systemLogMapper.selectList(null);
            return Result.success(list);
        }
        Page<SystemLog> page=new Page<>(current,size);
         systemLogMapper.selectPage(page, null);

        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }

    @Override
    public Result insertBatch(List<SystemLog> list){
        systemLogMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public IPage<SystemLog> getPage(int current, int size){
        IPage<SystemLog> page = new Page<SystemLog>(current,size);
        systemLogMapper.selectPage(page,null);
        return page;
    }

    @Override
    public Result deleteBatch(List<SystemLog> systemLogs) {
        int count = systemLogs.size();
        int successCount = 0;
        for(SystemLog systemLog: systemLogs){
            int result = systemLogMapper.deleteById(systemLog.getLog_id());
            successCount += result;
        }
        if(successCount == count){
            return Result.success(null);
        }
        else{
            return Result.fail(0,"删除失败");
        }
    }

    @Override
    public Result deleteBatchById(List<Integer> systemLogs) {
        int i = systemLogMapper.deleteBatchIds(systemLogs);
        if(i == systemLogs.size()){
            return Result.success(null);
        }
        else{
            return Result.fail(0,"删除失败");
        }
    }


//    @Override
//    public boolean saveBatch(Collection<SystemLog> entityList, int batchSize) {
//        return false;
//    }
//
//    @Override
//    public boolean saveOrUpdateBatch(Collection<SystemLog> entityList, int batchSize) {
//        return false;
//    }
//
//    @Override
//    public boolean updateBatchById(Collection<SystemLog> entityList, int batchSize) {
//        return false;
//    }
//
//    @Override
//    public boolean saveOrUpdate(SystemLog entity) {
//        return false;
//    }
//
//    @Override
//    public SystemLog getOne(Wrapper<SystemLog> queryWrapper, boolean throwEx) {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> getMap(Wrapper<SystemLog> queryWrapper) {
//        return null;
//    }
//
//    @Override
//    public <V> V getObj(Wrapper<SystemLog> queryWrapper, Function<? super Object, V> mapper) {
//        return null;
//    }
//
//    @Override
//    public BaseMapper<SystemLog> getBaseMapper() {
//        return null;
//    }
//
//    @Override
//    public Class<SystemLog> getEntityClass() {
//        return null;
//    }



}
