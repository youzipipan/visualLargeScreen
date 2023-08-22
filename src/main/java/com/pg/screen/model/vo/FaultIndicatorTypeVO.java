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
public class FaultIndicatorTypeVO {
    /**
     * 类型名字
     */
    private String typeName;
    /**
     * 故障指示器
     */
    private Long typeCount;
}
