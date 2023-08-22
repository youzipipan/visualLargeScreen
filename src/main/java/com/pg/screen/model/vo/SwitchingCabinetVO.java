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
public class SwitchingCabinetVO {
    /**
     * 开关站数量
     */
    private Long switchCount;

    /**
     * 环网柜数量
     */
    private Long cabinetCount;
}
