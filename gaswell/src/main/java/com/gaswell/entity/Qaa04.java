package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 套管记录
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qaa04对象", description = "套管记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qaa04 implements Serializable {



    private String jh;

    private String tglx;

    private String kgrq;

    private String xjrq;

    private String xjxh;

    private String tgmc;

    private BigDecimal wj;

    private String gj;

    private BigDecimal bh;

    private BigDecimal cd;

    private BigDecimal tgxs;

    private String sccj;


}
