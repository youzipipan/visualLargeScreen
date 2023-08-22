package com.pg.screen.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 重点关注故障线路 vo
 * @author c.chuang
 */
@Data
public class FocusOnFaultyLinesVo {

    private Long total;

    private List<FocusOnFaultyLines> focusOnFaultyLinesList;

    @Data
    public static class FocusOnFaultyLines {

        /**
         * 供电单位
         */
        private String unitName;

        /**
         * 线路名称
         */
        private String linesName;

        /**
         * 故障发生时间
         */
        private LocalDate faultyCreateTime;

        /**
         * 巡视时间
         */
        private LocalDate inspectionTime;

    }

}
