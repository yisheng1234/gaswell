package com.gaswell.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Lei Wang
 * @Date: 2021/12/09/ 0:23
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("systemlog")
public class SystemLog {
    @ExcelIgnore       //不参加excel导入导出
    @TableId(type = IdType.AUTO)
    private Integer log_id;
    @ExcelProperty(value = "操作人ID")
    private Integer operate_userid;
    @ExcelProperty(value = "操作人真实姓名")
    private String operate_realname;
    @ExcelProperty(value = "操作时间")
    private String operate_time;
    @ExcelProperty(value = "操作内容")
    private String operate_content;
    @ExcelProperty(value = "操作者IP")
    private String operate_ip;
}
