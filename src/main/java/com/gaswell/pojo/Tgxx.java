package com.gaswell.pojo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tgxx {
    @ExcelProperty(value = "序号")
    private Integer xh;
    @ExcelProperty(value = "井号")
    private String jh;
    @ExcelProperty(value = "单位")
    private String dw;
    @ExcelProperty(value = "区块名称")
    private String qkmc;
    @ExcelProperty(value = "开工日期")
    private String kgrq;
    @ExcelProperty(value = "下井日期")
    private String xjrq;
    @ExcelProperty(value = "套管序号")
    private String tgxh;
    @ExcelProperty(value = "套管类型")
    private String tglx;
    @ExcelProperty(value = "部件名称")
    private String bjmc;
    @ExcelProperty(value = "扶正器标志")
    private String fzqbz;
    @ExcelProperty(value = "粘砂套管标志")
    private String lstgbz;
    @ExcelProperty(value = "扣型")
    private String kx;
    @ExcelProperty(value = "外经（mm）")
    private String wj;
    @ExcelProperty(value = "钢级")
    private String gj;
    @ExcelProperty(value = "壁厚（mm）")
    private String bh;
    @ExcelProperty(value = "长度（m）")
    private String cd;
    @ExcelProperty(value = "下深（m）")
    private String xs;
    @ExcelProperty(value = "生产厂家")
    private String sccj;


}