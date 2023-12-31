package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.WorkOrderDistributionDao;
import com.pg.screen.model.vo.WorkOrderDistributionVo;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        log.info("------获取工单分布数据");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        int year = calendar.get(Calendar.YEAR);
        // 获取当前年第一天
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        String yearBegin = sdf.format(calendar.getTime());
        log.info("当前年第一天：" + yearBegin);
        // 获取第二年第一天
        calendar.clear();
        calendar.set(Calendar.YEAR, year+1);
        String yearEnd = sdf.format(calendar.getTime());
        log.info("下一年第一天：" + yearEnd);

        //检修
        List<Row> aroRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("ACTIVE_REPAIR_ORDER",yearBegin,yearEnd);
        //抢修
        List<Row> arroRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("ACTIVE_RUSH_REPAIR_ORDER", yearBegin, yearEnd);
        //报修
        List<Row> repairsRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("GZGD95598", yearBegin, yearEnd);
        //运维
        List<Row> omoRowList = workOrderDistributionDao
                .selectWorkOrderDistributionByUnit("INSPECTION_HISTORY_CONTROL", yearBegin, yearEnd);

        List<Long> aroList = buildList(aroRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> arroList = buildList(arroRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> repairsList = buildList(repairsRowList,"WORK_ORDER_UNIT","COUNTS");
        List<Long> omoList = buildList(omoRowList,"WORK_ORDER_UNIT","COUNTS");
        WorkOrderDistributionVo vo = new WorkOrderDistributionVo(
                aroList, arroList, repairsList, omoList);
        return new HttpResult<WorkOrderDistributionVo>().success(vo);
    }

}
