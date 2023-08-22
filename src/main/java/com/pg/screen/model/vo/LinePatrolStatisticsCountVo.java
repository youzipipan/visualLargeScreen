package com.pg.screen.model.vo;

import lombok.Data;

/**
 * @author c.chuang
 */
@Data
public class LinePatrolStatisticsCountVo {

    /**
     * 应巡视数量
     */
    private Long oughtToInspectionCount;

    /**
     * 已巡视数量
     */
    private Long alreadyInspectionCount;

    /**
     * 已巡视百分比
     */
    private Integer alreadyInspectionPercent;

    /**
     * 未巡视数量
     */
    private Long notInspectionCount;

    /**
     * 未巡视百分比
     */
    private Integer notInspectionPercent;

}
