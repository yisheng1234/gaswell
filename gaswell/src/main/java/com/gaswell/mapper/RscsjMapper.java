package com.gaswell.mapper;

import com.gaswell.pojo.Rscsj;
import com.gaswell.vo.ColumnNameAndCommentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RscsjMapper extends EasyBaseMapper<Rscsj> {
@Update("CREATE TABLE ${tableName}  (`r_id`int(11) NOT NULL AUTO_INCREMENT, " +
        "`r_xh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '序号', " +
        "`r_qt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '气田', " +
        "`r_qm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区名', " +
        "`r_zm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '站名', " +
        "`r_rq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日期',  " +
        "`r_scsj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产时间', " +
        "`r_jkwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '井口温度', " +
        "`r_qzzj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '气嘴直径', " +
        "`r_zgyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最高油压', " +
        "`r_zdyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最低油压', " +
        "`r_pjyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '平均油压', " +
        "`r_gjyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '关井油压', " +
        "`r_zgty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最高套压', " +
        "`r_zdty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最低套压', " +
        "`r_pjty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '平均套压', " +
        "`r_gjty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '关井套压', " +
        "`r_wsyl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外输压力', " +
        "`r_cq_db` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气-挡板', " +
        "`r_cq_wd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气-温度', " +
        "`r_cq_yl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气-压力', " +
        "`r_ce_yc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气-压差', " +
        "`r_ce_rcql` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气-日产气量', " +
        "`r_cy_cy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产液-产油', " +
        "`r_ce_cs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产液-产水', `" +
        "r_jy_yt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加药-液体', " +
        "`r_jy_gt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加药-固体', " +
        "`r_zyql` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '自用气量', " +
        "`r_rzc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日注醇', " +
        "`r_gjyyin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '关井原因', " +
        "`r_cuoslb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '措施类别', " +
        "`r_ceslb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测试类别', " +
        "`r_bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注', " +
        "PRIMARY KEY (`r_id`) USING BTREE, INDEX `id`(`r_id`) USING BTREE  ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;")
    void createTable(@Param("tableName") String tableName);
    @Select("select count(*) from information_schema.TABLES t where  t.TABLE_NAME ='${tableName}';")
    Boolean tableIsExist(@Param("tableName")String tableName);

    @Select("select r_rq,${properties} FROM rscsj where DATE_FORMAT( r_rq, '%Y-%m-%d')>#{date_down} AND DATE_FORMAT( r_rq, '%Y-%m-%d')<#{date_up}")
    List<Map<String,String>> showData(@Param("properties") String properties, @Param("date_down") String date_down, @Param("date_up") String date_up);
    @Select("select COLUMN_NAME,COLUMN_COMMENT from information_schema.COLUMNS where table_name = #{tableName} ORDER BY ORDINAL_POSITION")
    List<ColumnNameAndCommentVo> getColumnNameAndComment(@Param("tableName") String tableName);
}
