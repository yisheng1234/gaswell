package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qdb02对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qdb02 implements Serializable {



    private String jh;

    private String wgrq;

    private Float xh;

    private String gjmc;

    private String gjxh;

    private Float gjcd;

    private Float djsd;

    private String sccj;


}
