package com.gaswell.mapper;

import com.gaswell.pojo.RealTimeData;
import com.gaswell.pojo.ReciveCycleData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RealTimeDataMapper extends EasyBaseMapper<RealTimeData>{
    @Select("select * from realtimedata order by id desc LIMIT 1")
    RealTimeData findLatestData();
    @Select("select count(*) from ${tableName}")
    int dataNum(@Param("tableName") String tableName);
    @Update("CREATE TABLE ${tableName}  (" +
            " `id`int(11) NOT NULL AUTO_INCREMENT, " +
            "  " +
            " `jkyy` float, " +
            " `jkwd` float, " +
            " `jkty` float, " +
            " `jlyl` float, " +
            " `jlwd` float, " +
            " `jzyl` float, " +
            " `jzwd` float, " +
            " `schgyl` float, " +
            " `jlhgyl` float, " +
            " `ckyl` float, " +
            " `ckwd` float, " +
            " `yw_jl` float, " +
            " `qlyl` float, " +
            " `ywkg_jl` float, " +
            " `yw_sc` float, " +
            " `ywkg_sc` float, " +
            " `czyl` float, " +
            " `czll` float, " +
            " `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `datetime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qjmc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ywbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `zwbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `scq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `zm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qk` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfsbyc_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcjzl_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcjzl_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `shwljwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `xzls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `xzll` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qtpcxs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `iserror` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `isfixed` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " PRIMARY KEY (`id`) USING BTREE," +
            " INDEX `id`(`id`) USING BTREE " +
            " ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;")
    void createTable(@Param("tableName") String tableName);

    @Select("select count(*) from information_schema.TABLES t where  t.TABLE_NAME ='${tableName}';")
    Boolean tableIsExist(@Param("tableName")String tableName);
    @Select("select * from recivecycledata where iserror=0 order by id desc limit 1 ")
    ReciveCycleData findLastNorm();
    @Update("CREATE TABLE ${tableName}  (" +
            " `id`int(11) NOT NULL AUTO_INCREMENT, " +
            "  " +
            " `jkyy` float, " +
            " `jkwd` float, " +
            " `jkty` float, " +
            " `jlyl` float, " +
            " `jlwd` float, " +
            " `jzyl` float, " +
            " `jzwd` float, " +
            " `schgyl` float, " +
            " `jlhgyl` float, " +
            " `ckyl` float, " +
            " `ckwd` float, " +
            " `yw_jl` float, " +
            " `qlyl` float, " +
            " `ywkg_jl` float, " +
            " `yw_sc` float, " +
            " `ywkg_sc` float, " +
            " `czyl` float, " +
            " `czll` float, " +
            " `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `datetime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qjmc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ywbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `zwbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `scq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `zm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qk` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfsbyc_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcjzl_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcjzl_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `shwljwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `xzls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `xzll` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qtpcxs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `iserror` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `isfixed` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " PRIMARY KEY (`id`) USING BTREE," +
            " INDEX `id`(`id`) USING BTREE " +
            " ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;")
    void createTable_backup(String tableName_rcd_backup);
    @Select("select * FROM realtimedata where DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')<#{up}")
    List<RealTimeData> findOneDayData(@Param("down") String down, @Param("up") String up);
    @Select("select datetime,${properties} FROM realtimedata where DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')<#{up}")
    List<Map<String,String>> selectData(@Param("down") String down, @Param("up") String up, @Param("properties") String properties);
    @Select("select datetime,${properties} as ytyc FROM realtimedata where DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')<#{up}")
    List<Map<String,String>> selectDataYtyc(@Param("down") String down, @Param("up") String up, @Param("properties") String properties);
    @Select("select datetime,${properties}  FROM realtimedata ")
    List<Map<String,String>> showData(@Param("properties") String properties);
    @Select("select datetime,${properties} as ytyc FROM realtimedata ")
    List<Map<String,String>> showDataYtyc(@Param("properties") String properties);
    @Select("select id  FROM realtimedata where ${properties} not like ${value}")
    int selectIdByProperties(@Param("properties") String properties,@Param("value") String value);
    @Select("select ${properties} FROM realtimedata where datetime>#{down} AND datetime<#{up}")
    List<Float> findTimeSlotData(@Param("down") String down, @Param("up") String up,@Param("properties") String properties);
    @Select("SELECT * FROM realtimedata order by id desc limit ${dataNum}")
    List<RealTimeData> selectDataNum(int dataNum);
    @Select("select * FROM realtimedata where DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')<#{up}")
    List<RealTimeData> selectDataByDate(@Param("down") String down, @Param("up") String up);
}
