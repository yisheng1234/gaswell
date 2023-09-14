package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 采气井月数据
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qba02对象", description = "采气井月数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qba02 implements Serializable  {

    private String jh;

    private String qm;

    private String dm;

    private String zm;

    private String ny;

    private BigDecimal scts;

    private String qzzj;

    private BigDecimal cqdb;

    private BigDecimal yy;

    private BigDecimal ty;

    private BigDecimal jkwd;

    private BigDecimal wsyl;

    private BigDecimal rcql;

    private BigDecimal rcyl;

    private BigDecimal rcsl;

    private BigDecimal ycql;

    private BigDecimal ycyl;

    private BigDecimal ycsl;

    private BigDecimal ncql;

    private BigDecimal ncyl;

    private BigDecimal ncsl;

    private BigDecimal ljcql;

    private BigDecimal ljcyl;

    private BigDecimal ljcsl;

    private BigDecimal yjgty;

    private BigDecimal yjyty;

    private BigDecimal yzcl;

    private String bz;

    private String qcdk;

    private BigDecimal xcql;

    private BigDecimal xyl;

    private BigDecimal xcsl;

    private String hzjh;


}
