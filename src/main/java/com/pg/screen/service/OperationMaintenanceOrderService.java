package com.pg.screen.service;

import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.InspectionHistoryControlDao;
import com.pg.screen.dao.OperationMaintenanceOrderDao;
import com.pg.screen.model.vo.XyDataCountVo;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 运维工单业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class OperationMaintenanceOrderService {

    @Resource
    private OperationMaintenanceOrderDao operationMaintenanceOrderDao;
    @Resource
    private InspectionHistoryControlDao inspectionHistoryControlDao;

    /**
     * 获取运维工单总数
     *
     * @return 运维工单总数
     */
    public HttpResult<Long> getTotal(String workOrderUnit) {
        //Long count = operationMaintenanceOrderDao.selectTotal(workOrderUnit);
        Long count = inspectionHistoryControlDao.selectTotal(workOrderUnit);
        log.info("运维工单总数：{}", count);
        return new HttpResult<Long>().success(count);
    }

    /**
     * 获取运维工单状态XY轴数据
     *
     * @return 运维工单数
     */
    public HttpResult<XyDataCountVo> getCountByStatus(String workOrderUnit) {
        /*Long missedOrderCount = operationMaintenanceOrderDao.selectCountByStatus(0,workOrderUnit);
        log.info("运维工单Y轴-未接单数量：{}", missedOrderCount);
        Long receivedOrderCount = operationMaintenanceOrderDao.selectCountByStatus(1,workOrderUnit);
        log.info("运维工单Y轴-已接单数量：{}", receivedOrderCount);
        Long patrolCount = operationMaintenanceOrderDao.selectCountByStatus(2,workOrderUnit);
        log.info("运维工单Y轴-巡视中数量：{}", patrolCount);
        Long finishedCount = operationMaintenanceOrderDao.selectCountByStatus(3,workOrderUnit);*/
        Long finishedCount = inspectionHistoryControlDao.selectTotal(workOrderUnit);
        log.info("运维工单Y轴-已完成数量：{}", finishedCount);
        List<Long> yAxisNameList = new LinkedList<>();
        yAxisNameList.add(0L);
        yAxisNameList.add(0L);
        yAxisNameList.add(0L);
        yAxisNameList.add(finishedCount);
        List<String> xAxisNameList = new LinkedList<>();
        xAxisNameList.add("未接单");
        xAxisNameList.add("已接单");
        xAxisNameList.add("巡视中");
        xAxisNameList.add("已完成");
        XyDataCountVo vo =
                new XyDataCountVo(xAxisNameList,yAxisNameList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

}
