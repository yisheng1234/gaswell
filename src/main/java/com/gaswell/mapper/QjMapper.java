package com.gaswell.mapper;

import com.gaswell.pojo.Qj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QjMapper extends EasyBaseMapper<Qj>{
    @Select("select ywbh from qj ")
    List<String> findAllJh();
    @Select("select ywbh from qj where ym=#{ym}")
    List<String> findJhByYm(@Param("ym") String ym);
}
