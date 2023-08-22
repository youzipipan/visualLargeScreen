package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.OperationMaintenanceOrderMapper;
import com.pg.screen.utils.TimeInterval;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.pg.screen.mapper.entity.table.OperationMaintenanceOrderTableDef.OPERATION_MAINTENANCE_ORDER;

/**
 * 运维工单 dao
 *
 * @author c.chuang
 */
@Repository
public class OperationMaintenanceOrderDao {

    @Resource
    private OperationMaintenanceOrderMapper operationMaintenanceOrderMapper;

    /**
     * 查询运维工单总数
     *
     * @return 运维工单总数
     */
    public Long selectTotal(String workOrderUnit) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        queryWrapper.where(
                OPERATION_MAINTENANCE_ORDER.INSPECTION_COMPLETION_TIME.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        if (StringUtils.isNotBlank(workOrderUnit)) {
            queryWrapper.and(OPERATION_MAINTENANCE_ORDER.WORK_ORDER_UNIT.eq(workOrderUnit));
        }
        return operationMaintenanceOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据状态查询运维工单数
     *
     * @param status 0未接单 1已接单 2巡视中 3已完成
     * @return 运维工单数
     */
    public Long selectCountByStatus(Integer status, String workOrderUnit) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (status == 0) {
            queryWrapper
                    .where(OPERATION_MAINTENANCE_ORDER.APP_STATUS.eq("未接单"));
        } else if (status == 1) {
            queryWrapper
                    .where(OPERATION_MAINTENANCE_ORDER.TASK_STATUS.eq("已接单"))
                    .and(OPERATION_MAINTENANCE_ORDER.APP_STATUS.eq("已接单"));
        } else if (status == 2) {
            queryWrapper
                    .where(OPERATION_MAINTENANCE_ORDER.TASK_STATUS.eq("已接单"))
                    .and(OPERATION_MAINTENANCE_ORDER.APP_STATUS.eq("到达现场"));
        } else if (status == 3) {
            queryWrapper
                    .where(OPERATION_MAINTENANCE_ORDER.TASK_STATUS.eq("已处理"));
        } else {
            throw new RuntimeException("根据状态查询运维工单数-status参数错误");
        }
        if (StringUtils.isNotBlank(workOrderUnit)) {
            queryWrapper.and(OPERATION_MAINTENANCE_ORDER.WORK_ORDER_UNIT.eq(workOrderUnit));
        }
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        queryWrapper.and(OPERATION_MAINTENANCE_ORDER
                .INSPECTION_COMPLETION_TIME.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        return operationMaintenanceOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询巡视完成时间
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 巡视完成时间
     */
    public List<LocalDate> selectInspectionCompletionTime(LocalDate beginDate, LocalDate endDate) {
        beginDate = beginDate.minusMonths(1);
        String sql = "SELECT TO_CHAR(INSPECTION_COMPLETION_TIME, 'yyyy-MM-dd') DATES " +
                "     FROM OPERATION_MAINTENANCE_ORDER " +
                "     WHERE INSPECTION_COMPLETION_TIME BETWEEN ? AND ?";
        List<Row> rows = Db.selectListBySql(sql, beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return null;
        }
        List<LocalDate> resultList = new LinkedList<>();
        for (Row row : rows) {
            resultList.add(
                    LocalDate.parse(row.getString("DATES"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    ));
        }
        return resultList;
    }

    /**
     * 查询未巡视数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 未巡视数量
     */
    public Long selectNotInspectionTourCount(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(OPERATION_MAINTENANCE_ORDER.INSPECTION_COMPLETION_TIME.isNull());
        if (beginDate != null && endDate != null) {
            queryWrapper.and(
                    OPERATION_MAINTENANCE_ORDER.ARRIVAL_SCENE_TIME.between(beginDate, endDate));
        }
        return operationMaintenanceOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询未巡视数量根据单位
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectNotInspectionTourByUnit(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(OPERATION_MAINTENANCE_ORDER.WORK_ORDER_UNIT),
                        count().as("COUNTS"))
                .from(OPERATION_MAINTENANCE_ORDER)
                .where(OPERATION_MAINTENANCE_ORDER.INSPECTION_COMPLETION_TIME.isNull())
                .groupBy(OPERATION_MAINTENANCE_ORDER.WORK_ORDER_UNIT);
        if (beginDate != null && endDate != null) {
            queryWrapper.and(
                    OPERATION_MAINTENANCE_ORDER.ARRIVAL_SCENE_TIME.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

    /**
     * 查询巡视状态
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectInspectionTourByStatus(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(OPERATION_MAINTENANCE_ORDER.TASK_STATUS,
                        count().as("COUNTS"))
                .from(OPERATION_MAINTENANCE_ORDER)
                .groupBy(OPERATION_MAINTENANCE_ORDER.TASK_STATUS);
        if (beginDate != null && endDate != null) {
            queryWrapper.and(
                    OPERATION_MAINTENANCE_ORDER.ARRIVAL_SCENE_TIME.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

}
