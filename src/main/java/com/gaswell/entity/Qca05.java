package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qca05对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qca05 implements Serializable {

    private String jh;

    private String csrq;

    private String jsmx;

    private BigDecimal stl;

    private BigDecimal dcxs1;

    private BigDecimal dcxs2;

    private BigDecimal dcxs3;

    private BigDecimal jcxs;

    private BigDecimal lfbc;

    private String lfcdl;

    private BigDecimal sbpxs;

    private BigDecimal zbpxs;

    private BigDecimal bjjl1;

    private BigDecimal bjjl2;

    private BigDecimal bjjl3;

    private BigDecimal bjjl4;

    private BigDecimal bjjj;

    private BigDecimal wtyl;

    private BigDecimal fjyj;

    private BigDecimal tcbj;

    private BigDecimal zsbj;

    private BigDecimal fhbj1;

    private BigDecimal fhbj2;

    private String bz;

    private String yljh;

    private BigDecimal stln;

    private BigDecimal stlw;

    private BigDecimal qczbly;

    private BigDecimal wcql;

    private String wdxz;

    private String sfjxwdxz;


}
