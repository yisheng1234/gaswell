package com.gaswell.mapper;

import com.gaswell.pojo.Diagnosis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisMapper extends EasyBaseMapper<Diagnosis>{
    @Select("select DISTINCT jh from diagnosis ")
    List<String> selectAllJh();

    @Select("select * from diagnosis where jh=#{jh} order by cjsj desc LIMIT 1")
    Diagnosis findLatestData(@Param("jh") String jh);
}
