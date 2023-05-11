package com.gaswell.mapper;

import com.gaswell.pojo.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:53
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Repository
public interface ParamsMapper extends EasyBaseMapper<Params>{
    @Select("select * from params where param_name=#{name}")
    Params selectByName(@Param("name") String name);
    @Select("select * from params where param_name=#{name} and param_jh=#{jh}")
    Params selectByNameaAndJh(@Param("name") String name, @Param("jh") String jh);
}
