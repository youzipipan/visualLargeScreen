package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.WorkOrderDistributionDao;
import com.pg.screen.model.vo.WorkOrderDistributionVo;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pg.screen.utils.ToolUtils.buildList;

/**
 * 工单分布业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class WorkOrderDistributionService {

    @Resource
    private WorkOrderDistributionDao workOrderDistributionDao;

    /**
     * 获取工单分布数据
     *
     * @return 工单分布数据
     */
    public HttpResult<WorkOrderDistributionVo> getWorkOrderDistributionCount() {
        List<Row> aroRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("ACTIVE_REPAIR_ORDER");
        List<Row> arroRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("ACTIVE_RUSH_REPAIR_ORDER");
        List<Row> repairsRowList = workOrderDistributionDao.selectRepairsOrderByUnit();
        List<Row> omoRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("OPERATION_MAINTENANCE_ORDER");
        List<Long> aroList = buildList(aroRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> arroList = buildList(arroRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> repairsList = buildList(repairsRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> omoList = buildList(omoRowList,"WORK_ORDER_UNIT","COUNTS");
        WorkOrderDistributionVo vo = new WorkOrderDistributionVo(
                aroList, arroList, repairsList, omoList);
        return new HttpResult<WorkOrderDistributionVo>().success(vo);
    }

}
