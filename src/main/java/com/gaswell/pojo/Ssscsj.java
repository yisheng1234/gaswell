package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author Lei Wang
 * @Date: 2021/12/09/ 0:18
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ssscsj {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer ss_id;

    @ExcelProperty(value = "英文井号")
    private String ss_ywjh;
    @ExcelProperty(value = "采集日期")
    private String ss_cjrq;
    @ExcelProperty(value = "采集时间")
    private String ss_cjsj;
    @ExcelProperty(value = "油压")
    private String ss_yy;
    @ExcelProperty(value = "套压")
    private String ss_ty;
    @ExcelProperty(value = "温度")
    private String ss_wd;
    @ExcelProperty(value = "产气量")
    private String ss_cql;

}
