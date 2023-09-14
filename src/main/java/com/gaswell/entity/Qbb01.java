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
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Qbb01对象", description = "")
public class Qbb01 implements Serializable {


    private String ny;

    private String qtmc;

    private String qcdk;

    private BigDecimal cqjzs;

    private BigDecimal cqjkjs;

    private BigDecimal jsjkjs;

    private BigDecimal jyjkjs;

    private BigDecimal rcql;

    private BigDecimal rcyl;

    private BigDecimal rcsl;

    private BigDecimal ycql;

    private BigDecimal ycyl;

    private BigDecimal ycsl;

    private BigDecimal ncql;

    private BigDecimal ncyl;

    private BigDecimal ncsl;

    private BigDecimal lqcql;

    private BigDecimal ljcyl;

    private BigDecimal ljcsl;

    private BigDecimal cqsd;

    private BigDecimal cccd;

    private BigDecimal count;


}
