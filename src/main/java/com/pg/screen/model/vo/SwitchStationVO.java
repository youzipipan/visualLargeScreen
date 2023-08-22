package com.pg.screen.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengzhi.wang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwitchStationVO {
    /**
     * 开关站内总数量
     */
    private Long switchStationCount;
    /**
     * 开关站内-三遥数量
     */
    private Long threeRemoteCount;

    /**
     * 开关站内-普通数量
     */
    private Long normalCount;
}
