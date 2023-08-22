package com.pg.screen.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 中压线路VO
 *
 * @author pengzhi.wang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediumVoltageLineVO {

    /**
     * 架空线路总条数
     */
    private Long overheadLineCount;

    /**
     * 架空线路总长度
     */
    private BigDecimal overheadLineTotal;

    /**
     * 电缆线路总条数
     */
    private Long cableLineCount;

    /**
     * 电缆线路总长度
     */
    private BigDecimal cableLineTotal;

    /**
     * 混合线路总条数
     */
    private Long hybridLineCount;

    /**
     * 混合线路总长度
     */
    private BigDecimal hybridLineTotal;

    /**
     * 馈线数量
     */
    private Long feederCount;

}
