package com.gaswell.mapper;

import com.gaswell.pojo.AlarmRecord;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRecordMapper extends EasyBaseMapper<AlarmRecord>{
    @Select("select DISTINCT jh from alarm_record ")
    List<String> selectAllJh();
    @Select("select DISTINCT alarm_category from alarm_record ")
    List<String> selectAllCategory();
    @Select("select DISTINCT record from alarm_record ")
    List<String> selectAllRecord();
}
