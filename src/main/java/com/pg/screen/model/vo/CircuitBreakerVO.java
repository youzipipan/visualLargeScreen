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
public class CircuitBreakerVO {

    /**
     * 断路器
     */
    private Long circuitBreakerCount;
    /**
     * 三遥
     */
    private Long threeRemoteCount;
    /**
     * 普通
     */
    private Long normalCount;
}
