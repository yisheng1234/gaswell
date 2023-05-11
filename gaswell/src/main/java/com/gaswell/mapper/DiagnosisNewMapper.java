package com.gaswell.mapper;

import com.gaswell.pojo.Diagnosis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiagnosisNewMapper extends EasyBaseMapper<Diagnosis>{
    @Select("select DISTINCT jh from diagnosis ")
    List<String> selectAllJh();

    @Select("select * from diagnosis where jh=#{jh} order by cjsj desc LIMIT 1")
    Diagnosis findLatestData(@Param("jh") String jh);
    @Select("select Max(cjsj) from diagnosis ")
    String selectLastTime();
    @Select("select * from diagnosis where jh=#{jh} and cjsj<=#{lastTime} and cjsj>#{secondLastTime}")
    List<Diagnosis> lastFiveNewData(@Param("jh") String jh ,@Param("secondLastTime") String secondLastTime,@Param("lastTime") String lastTime);
    @Select("select * from diagnosis where jh=#{jh} and category in(#{distortion},#{normal}) order by cjsj desc limit #{N}")
    List<Diagnosis> findData(@Param("jh")String jh, @Param("N") int N, @Param("distortion") String distortion
            , @Param("normal")String normal);
    @Select("select * from diagnosis where  jh=#{jh}")
    List<Diagnosis> findDataByJh(@Param("jh") String jh);
    @Select("select DISTINCT cjsj from diagnosis where jh=#{jh} and cjsj<=#{newAddDatum} order by cjsj desc LIMIT #{N}" )
    List<String> findDateList(@Param("N")int N,@Param("jh") String jh,@Param("newAddDatum") String newAddDatum);
    @Select("select DISTINCT cjsj from diagnosis where jh=#{jh} and cjsj>#{secondLastTime} and cjsj <=#{lastTime} order by cjsj asc " )
    List<String> findNewDataList(@Param("jh")String jh, @Param("secondLastTime") String secondLastTime, @Param("lastTime")String lastTime);
    @Select("select * from diagnosis where jh=#{jh} and cjsj=#{s}")
    List<Diagnosis> findDataByTime(@Param("jh") String jh, @Param("s") String s);
}
