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
public class CircuitBreakerSwitchVO {

    /**
     * 断路器开关
     */
    private Long circuitBreakerSwitchCount;
    /**
     * 开关三遥
     */
    private Long threeRemoteSwitchCount;
    /**
     * 开关普通
     */
    private Long normalSwitchCount;
}
