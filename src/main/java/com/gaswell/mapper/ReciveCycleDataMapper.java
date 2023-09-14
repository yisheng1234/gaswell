package com.gaswell.mapper;

import com.gaswell.pojo.ReciveCycleData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReciveCycleDataMapper extends EasyBaseMapper<ReciveCycleData>{
    @Select("select * from recivecycledata  order by id desc LIMIT 1")
    ReciveCycleData findLatestData();

    @Update("CREATE TABLE ${tableName}  (" +
            " `id`int(11) NOT NULL AUTO_INCREMENT, " +
            "  " +
            " `jkwd` float, " +
            " `tgyl` float, " +
            " `ygyl` float, " +
            " `wsyl` float, " +
            " `cyell` float, " +
            " `cyoull` float, " +
            " `cshuill` float, " +
            " `cqill` float, " +
            " `jyyl` float, " +
            " `jyll` float, " +
            " `qjkgzjl` float, " +
            " `ppkgzjl` float, " +
            " `zckgzjl` float, " +
            " `deviceId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `datetime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `rtuVoltage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `rtuAi` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `qjmc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ywbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `zwbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `scq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfsbyc_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcljz_java` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjy_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfshw_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `sfjk_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `ppjjzl_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
            " `jcljz_py` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, " +
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
    @Select("select id  FROM realtimedata where ${properties} not like ${value} order by id desc limit 1")
    int selectIdByProperties(@Param("properties") String properties,@Param("value") String value);
    @Select("select datetime,${properties} FROM recivecycledata where  datetime>#{down} AND datetime<#{up}")
    List<Map<String,String>> selectData(@Param("down") String down, @Param("up") String up, @Param("properties") String properties);
    @Select("select datetime,${properties} as ytyc FROM recivecycledata where datetime>#{down} AND datetime<#{up}")
    List<Map<String,String>> selectDataYtyc(@Param("down") String down, @Param("up") String up, @Param("properties") String properties);
    @Select("select * FROM recivecycledata where DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')>#{down} AND DATE_FORMAT( datetime, '%Y-%m-%d %h:%i:%s')<#{up}")
    List<ReciveCycleData> findOneDayData(@Param("down") String down, @Param("up") String up);
    @Select("select datetime,${properties}  FROM recivecycledata ")
    List<Map<String,String>> showData(@Param("properties") String properties);
    @Select("select datetime,${properties} as ytyc FROM recivecycledata ")
    List<Map<String,String>> showDataYtyc(@Param("properties") String properties);
}
