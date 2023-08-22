package com.pg.screen.model.vo;

import lombok.Data;

/**
 * 停电信息 vo
 *
 * @author c.chuang
 */
@Data
public class OutageInformationVo {

    /**
     * 计划停电
     */
    private OutageInformation plannedOutage;

    /**
     * 故障停电
     */
    private OutageInformation faultOutage;

    /**
     * 临时停电
     */
    private OutageInformation temporaryOutage;

    @Data
    public static class OutageInformation {

        /**
         * 停电类型总数
         */
        private Long blackoutTypeTotal;

        /**
         * 影响用户数
         */
        private Long users;

        /**
         * 涉及台区数
         */
        private Long districts;

    }

}
