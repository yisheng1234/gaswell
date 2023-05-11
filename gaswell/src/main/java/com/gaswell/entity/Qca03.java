package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qca03对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qca03 implements Serializable {



    private String jh;

    private String csdm;

    private LocalDate csrq;

    private BigDecimal rcql;

    private BigDecimal rcsl;

    private BigDecimal jkwd;

    private BigDecimal yy;

    private BigDecimal ty;

    private BigDecimal csds;

    private BigDecimal yqqsdsd;

    private BigDecimal qsdyl;

    private BigDecimal qsdwd;

    private BigDecimal yqzzdsd;

    private BigDecimal zzdwl;

    private BigDecimal zzdwd;

    private BigDecimal zsyltd;

    private BigDecimal zswdtd;

    private BigDecimal qczbsd;

    private String yljh;

    private String bz;


}
