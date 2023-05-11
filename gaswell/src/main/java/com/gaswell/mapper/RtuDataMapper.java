package com.gaswell.mapper;

import com.gaswell.pojo.RtuData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RtuDataMapper extends EasyBaseMapper<RtuData>{

    @Select("select cjsj,${properties} FROM rtu_data_qj where DATE_FORMAT( cjsj, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( cjsj, '%Y-%m-%d %h:%i:%s')<#{up} and jh=#{ywbh}")
    List<Map<String,String>> selectData(@Param("down") String down, @Param("up") String up, @Param("properties") String properties,@Param("ywbh") String ywbh);
    @Select("select cjsj,${properties} as ytyc FROM rtu_data_qj where DATE_FORMAT( cjsj, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( cjsj, '%Y-%m-%d %h:%i:%s')<#{up} and jh=#{ywbh}")
    List<Map<String,String>> selectDataYtyc(@Param("down") String down, @Param("up") String up, @Param("properties") String properties,@Param("ywbh") String ywbh);
    @Select("select DISTINCT jh from rtu_data_qj ")
    List<String> selectAllJh();

    @Select("select jh,cjsj,J_YGYL AS YGYL,J_TGYL AS TGYL,J_ZQYL AS ZQYL,J_YGWD AS YGWD,J_WSWD AS WSWD,J_FCS AS FCS,J_FOS AS FOS,J_VVC AS VVC,Z_JZWD AS JZWD,Z_SJHWD AS SJHWD,Z_JRHWD AS JRHWD,Z_JZYL AS JZYL,Z_SJHYL AS SJHYL from rtu_data_qj where jh=#{jh} order by cjsj desc LIMIT 1")
    RtuData findLatestData(@Param("jh") String jh);
}
