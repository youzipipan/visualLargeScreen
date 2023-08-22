package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.model.vo.WorkOrderDistributionVo;
import com.pg.screen.model.vo.XyDataCountVo;
import com.pg.screen.service.*;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配网工单接口
 *
 * @author c.chuang
 */
@RestController
@RequestMapping("/worker_order/")
@CrossOrigin(origins = "*")
public class WorkerOrderController {

    @Resource
    private RepairsOrderService repairsOrderService;

    @Resource
    private ActiveRushRepairOrderService activeRushRepairOrderService;

    @Resource
    private ActiveRepairOrderService activeRepairOrderService;

    @Resource
    private OperationMaintenanceOrderService operationMaintenanceOrderService;

    @Resource
    private WorkOrderDistributionService workOrderDistributionService;

    /**
     * 获取报修工单总数
     *
     * @param workerUnit 工单单位
     * @return 报修工单总数
     */
    @GetMapping("repairs_total")
    public HttpResult<Long> getRepairsTotal(String workerUnit) {
        if (StringUtils.isNotBlank(workerUnit)) {
            workerUnit = WorkOrderUnitEnum.getName(workerUnit);
        }
        return repairsOrderService.getTotal(workerUnit);
    }


    /**
     * 获取报修状态工单数
     *
     * @param workerUnit 工单单位
     * @return 报修状态工单数
     */
    @GetMapping("repairs_xy_count")
    public HttpResult<XyDataCountVo> getRepairsCountByStatus(String workerUnit) {
        if (StringUtils.isNotBlank(workerUnit)) {
            workerUnit = WorkOrderUnitEnum.getName(workerUnit);
        }
        return repairsOrderService.getCountByStatus(workerUnit);
    }

    /**
     * 获取主动抢修工单总数
     *
     * @param workOrderUnit 工单单位
     * @return 主动抢修工单总数
     */
    @GetMapping("arro_total")
    public HttpResult<Long> getActiveRushRepairOrderTotal(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return activeRushRepairOrderService.getTotal(workOrderUnit);
    }

    /**
     * 获取主动抢修工单状态XY轴数据
     *
     * @param workOrderUnit 工单单位
     * @return 主动抢修工单状态XY轴数据
     */
    @GetMapping("arro_xy_counts")
    public HttpResult<XyDataCountVo> getActiveRushRepairOrderCountByStatus(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return activeRushRepairOrderService.getCountByStatus(workOrderUnit);
    }

    /**
     * 获取主动检修工单总数
     *
     * @param workOrderUnit 工单单位
     * @return 主动检修工单总数
     */
    @GetMapping("aro_total")
    public HttpResult<Long> getActiveRepairOrderTotal(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return activeRepairOrderService.getTotal(workOrderUnit);
    }

    /**
     * 获取主动检修工单状态XY轴数据
     *
     * @param workOrderUnit 工单单位
     * @return 主动检修工单状态XY轴数据
     */
    @GetMapping("aro_xy_count")
    public HttpResult<XyDataCountVo> getActiveRepairOrderCountByStatus(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return activeRepairOrderService.getCountByStatus(workOrderUnit);
    }

    /**
     * 获取运维工单总数
     *
     * @param workOrderUnit 工单单位
     * @return 运维工单总数
     */
    @GetMapping("omo_total")
    public HttpResult<Long> selectTotal(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return operationMaintenanceOrderService.getTotal(workOrderUnit);
    }

    /**
     * 获取运维工单状态XY轴数据
     *
     * @param workOrderUnit 工单单位
     * @return 运维工单数
     */
    @GetMapping("omo_xy_count")
    public HttpResult<XyDataCountVo> getCountByStatus(String workOrderUnit) {
        if (StringUtils.isNotBlank(workOrderUnit)) {
            workOrderUnit = WorkOrderUnitEnum.getName(workOrderUnit);
        }
        return operationMaintenanceOrderService.getCountByStatus(workOrderUnit);
    }

    /**
     * 获取工单分布数据
     *
     * @return 工单分布数据
     */
    @GetMapping("wod_xy_count")
    public HttpResult<WorkOrderDistributionVo> getWorkOrderDistributionCount() {
        return workOrderDistributionService.getWorkOrderDistributionCount();
    }

}
