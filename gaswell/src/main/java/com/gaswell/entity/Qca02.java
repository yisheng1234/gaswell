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
@ApiModel(value = "Qca02对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qca02 implements Serializable {



    private String jh;

    private String csdm;

    private String csrq;

    private BigDecimal cs;

    private BigDecimal yxhd;

    private BigDecimal fczs;

    private BigDecimal fcxs;

    private BigDecimal wzll1;

    private BigDecimal cnjj;

    private BigDecimal fcxl;

    private BigDecimal wzll2;

    private String bz;

}
