package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 供电可靠性管理 vo
 * @author c.chuang
 */
@Data
public class ReliabilityManagementVo {

    /**
     * 可靠率
     */
    private BigDecimal reliability = BigDecimal.ZERO;

    /**
     * 平均停电时间
     */
    private BigDecimal blackoutTime = BigDecimal.ZERO;

    /**
     * 停电时户数
     */
    private BigDecimal hourFamilyNum = BigDecimal.ZERO;

    /**
     * 可考虑同比
     */
    private BigDecimal reliableYear = BigDecimal.ZERO;

    /**
     * 平均停电时间同比
     */
    private BigDecimal blackoutTimeYear = BigDecimal.ZERO;

    /**
     * 停电时户数同比
     */
    private BigDecimal hourFamilyNumYear = BigDecimal.ZERO;

}
