package com.pg.screen.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 超长停电情况 vo
 *
 * @author c.chuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProlongedPowerFailureVo extends XyDataCountVo {

    /**
     * 累计停电小时
     */
    private BigDecimal hour;

    /**
     * 总数
     */
    private Long total;

}
