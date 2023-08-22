package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 公变经济运行情况 vo
 *
 * @author c.chuang
 */
@Data
public class EconomicOperationVo {

    /**
     * 0-20-数量
     */
    private Long zeroToTwentyCount;

    /**
     * 0-20-百分比
     */
    private Integer zeroToTwentyPercent;

    /**
     * 20-50-数量
     */
    private Long twentyToFiftyCount;

    /**
     * 20-50-百分比
     */
    private Integer twentyToFiftyPercent;

    /**
     * 50-70-数量
     */
    private Long fiftyToSeventyCount;

    /**
     * 50-70-百分比
     */
    private Integer fiftyToSeventyPercent;

    /**
     * 70-100-数量
     */
    private Long seventyToHundredCount;

    /**
     * 70-100-百分比
     */
    private Integer seventyToHundredPercent;

    /**
     * 100以上-数量
     */
    private Long overHundredCount;

    /**
     * 100以上-百分比
     */
    private Integer overHundredPercent;

}
