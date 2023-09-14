package com.gaswell.mapper;

import com.gaswell.pojo.Yscsj;
import com.gaswell.vo.ColumnNameAndCommentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

@Repository
public interface YscsjMapper extends EasyBaseMapper<Yscsj>{
    @Update("CREATE TABLE ${tableName}  ( `y_id`int(11) NOT NULL AUTO_INCREMENT, " +
            "`y_xh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '序号', " +
            "`y_qt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '气田', " +
            "`y_qm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区名', " +
            "`y_zm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '站名', " +
            "`y_ny` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年月', " +
            "`y_scts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产天数', " +
            "`y_qzzj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '气嘴直径', " +
            "`y_cqdb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测气挡板', " +
            "`y_yy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '油压', " +
            "`y_ty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '套压', " +
            "`y_jkwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '井口温度', " +
            "`y_wsyl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外输压力', " +
            "`y_rcq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日产气', " +
            "`y_rcs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日产水', " +
            "`y_rcy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日产油', " +
            "`y_ycq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月产气', " +
            "`y_ycs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月产水', " +
            "`y_ycy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月产油', " +
            "`y_ncq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年产气', " +
            "`y_ncs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年产水', " +
            "`y_ncy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年产油', " +
            "`y_ljcq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '累计产气', " +
            "`y_ljcs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '累计产水', " +
            "`y_ljcy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '累计产油', " +
            "`y_yjy_yt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月加药—液体', " +
            "`y_yjy_gt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月加药—固体', " +
            "`y_yzc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月注醇', " +
            "`y_bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注', " +
            " PRIMARY KEY (`y_id`) USING BTREE, INDEX `id`(`y_id`) USING BTREE  ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;")
    void createTable(@Param("tableName") String tableName);
    @Select("select count(*) from information_schema.TABLES t where  t.TABLE_NAME ='${tableName}';")
    Boolean tableIsExist(@Param("tableName")String tableName);
    @Select("select y_ny,${properties} FROM yscsj where y_ny>#{date_down} AND y_ny<#{date_up}")
    List<Map<String,String>> showData(@Param("properties") String properties, @Param("date_down") String date_down, @Param("date_up") String date_up);
    @Select("select COLUMN_NAME,COLUMN_COMMENT from information_schema.COLUMNS where table_name = #{tableName} ORDER BY ORDINAL_POSITION")
    List<ColumnNameAndCommentVo> getColumnNameAndComment(String tableName);
}
