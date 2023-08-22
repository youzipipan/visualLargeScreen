package com.pg.screen.mapper.entity;

import lombok.Data;

/**
 * 缺陷处理统计 vo
 * @author c.chuang
 */
@Data
public class DefectLibraryCountVo {

    /**
     * 缺陷数量
     */
    private Long sum;

    /**
     * 未消缺数量
     */
    private Long notCount;

    /**
     * 已消缺数量
     */
    private Long alreadyCount;

    /**
     * 未消缺百分比
     */
    private Integer notPercent;

    /**
     * 已消缺百分比
     */
    private Integer alreadyPercent;

}
