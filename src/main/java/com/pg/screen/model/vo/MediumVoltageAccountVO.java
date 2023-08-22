package com.pg.screen.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 配电变压器VO
 *
 * @author pengzhi.wang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediumVoltageAccountVO {
    /**
     * 公变台数
     */
    private BigDecimal publicTransformerCount;
    /**
     * 专变台数
     */
    private BigDecimal specialTransformerCount;

    /**
     * 公变容量
     */
    private BigDecimal publicTransformerCapacity;

    /**
     * 专变容量
     */
    private BigDecimal specialTransformerCapacity;

    /**
     * 变压器总数量
     */
    private BigDecimal transformersCount;

}
