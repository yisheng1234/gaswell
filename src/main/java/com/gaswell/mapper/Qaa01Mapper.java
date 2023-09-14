package com.gaswell.mapper;

import com.gaswell.entity.Qaa01;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 气井基础信息 Mapper 接口
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Repository
public interface Qaa01Mapper extends EasyBaseMapper<Qaa01> {
    @Select("select count(1) from qaa01")
    double dataCount();

    // 查询全部气田名称
    @Select("select distinct QTMC from qaa01")
    List<String> selectQTMC();

    // 查询全部区名
    @Select("select distinct QM from qaa01")
    List<String> selectQM();

    // 查询全部站名
    @Select("select distinct ZM from qaa01")
    List<String> selectZM();

    @Select("select DISTINCT jh from qaa01 ")
    List<String> selectJh();


}
