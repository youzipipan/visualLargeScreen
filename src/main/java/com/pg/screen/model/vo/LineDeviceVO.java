package com.pg.screen.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pengzhi.wang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineDeviceVO {
    /**
     * 故障指示器总数
     */
    private Long count;

    /**
     * 故障指示器类型VO
     */
    private List<FaultIndicatorTypeVO> type;


}
