package com.pg.screen.model.vo;

import com.pg.screen.enums.WorkOrderUnitEnum;
import lombok.Data;

import java.util.List;

/**
 * 工单分布 vo
 *
 * @author c.chuang
 */
@Data
public class WorkOrderDistributionVo {

    public WorkOrderDistributionVo(List<Long> yActiveRepairOrderList,
                                   List<Long> yActiveRushRepairOrderList,
                                   List<Long> yRepairsOrderList,
                                   List<Long> yOperationMaintenanceOrderList) {
        this.yActiveRepairOrderList = yActiveRepairOrderList;
        this.yActiveRushRepairOrderList = yActiveRushRepairOrderList;
        this.yRepairsOrderList = yRepairsOrderList;
        this.yOperationMaintenanceOrderList = yOperationMaintenanceOrderList;
    }

    /**
     * X轴值
     */
    private List<String> xAxisNameList = WorkOrderUnitEnum.getShortName();

    /**
     * Y轴-主动检修值
     */
    private List<Long> yActiveRepairOrderList;

    /**
     * Y轴-主动抢修值
     */
    private List<Long> yActiveRushRepairOrderList;

    /**
     * Y轴-报修值
     */
    private List<Long> yRepairsOrderList;

    /**
     * Y轴-运维工单值
     */
    private List<Long> yOperationMaintenanceOrderList;

}
