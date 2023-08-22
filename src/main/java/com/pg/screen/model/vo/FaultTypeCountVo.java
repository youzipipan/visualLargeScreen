package com.pg.screen.model.vo;

import lombok.Data;

/**
 * 故障类型数量 vo
 *
 * @author c.chuang
 */
@Data
public class FaultTypeCountVo {

    /**
     * 重合不良count
     */
    private Long misalignmentCount = 0L;

    /**
     * 重合良好
     */
    private Long goodCoincideCount = 0L;

    /**
     * 接地故障
     */
    private Long groundFaultCount = 0L;

    /**
     * 支线故障
     */
    private Long branchFault = 0L;

}
