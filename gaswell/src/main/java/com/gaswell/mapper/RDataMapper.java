package com.gaswell.mapper;

import com.gaswell.pojo.RData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository

public interface RDataMapper extends EasyBaseMapper<RData>{
    @Select("select * from rtu_data where jh=#{jh} order by cjsj desc LIMIT 1")
    RData findLast(@Param("jh") String jh);
}
