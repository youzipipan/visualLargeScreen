package com.pg.screen.mapper.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DefectLibraryInfoVo {

    /**
     * 供电单位
     */
    private String name;

    /**
     * 巡检数量
     */
    private int inspectionSum;

    /**
     * 发现消缺数量
     */
    private int solveSum;

    /**
     * 已消缺数量
     */
    private int completeSolve;

    /**
     * 平均时长
     */
    private String aveDate;

    /**
     * 缺陷处理率
     */
    private BigDecimal rate;

}
