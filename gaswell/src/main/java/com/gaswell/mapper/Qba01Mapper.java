package com.gaswell.mapper;

import com.gaswell.entity.Qba01;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 采气井日数据 Mapper 接口
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Repository

public interface Qba01Mapper extends EasyBaseMapper<Qba01> {
    @Select("select * from qba01 order by rq desc limit 1")
    Qba01 selectLastOne();

    @Select("select * from qba01")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    @ResultType(Qba01.class)
    void streamQuery(ResultHandler<Qba01> handler);



    Cursor<Qba01> streamQuery();

}
