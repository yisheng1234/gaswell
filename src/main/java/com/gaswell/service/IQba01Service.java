package com.gaswell.service;

import com.gaswell.entity.Qba01;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaswell.vo.Qba01Vo;
import com.gaswell.vo.Result;
import org.apache.ibatis.cursor.Cursor;

/**
 * <p>
 * 采气井日数据 服务类
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
public interface IQba01Service extends IService<Qba01> {

    Result insertOne(Qba01 qba01);

    Result updateOne(Qba01 qba01);

    Result selectProperties( Qba01 qba01,int sort,int departnent);

    Result selectAll(int current, int size,String jh,int sort);

    Qba01 selectLastOne();

    Qba01 setByDate(String s,String jh);

    /**
     * 获取最新的数据
     * @param jh
     * @return
     */
    Qba01 setByDateLast(String jh);

    Cursor<Qba01> streamQuery();

    Result selectByMutiProperties(Qba01Vo qba01Vo,String date,int sort,int department);

    Result findQm();
    Result findZm();
    Result findDm();
    Result findZyq();
    Result findQkmc();


}
