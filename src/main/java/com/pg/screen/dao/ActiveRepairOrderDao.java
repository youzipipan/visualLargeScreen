package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.ActiveRepairOrderMapper;

import javax.annotation.Resource;

import com.pg.screen.utils.TimeInterval;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import static com.pg.screen.mapper.entity.table.ActiveRepairOrderTableDef.ACTIVE_REPAIR_ORDER;

/**
 * 主动检修 dao
 *
 * @author c.chuang
 */
@Repository
public class ActiveRepairOrderDao {

    @Resource
    private ActiveRepairOrderMapper activeRepairOrderMapper;

    /**
     * 查询主动检修工单总数
     *
     * @param workOrderUnit 工单单位
     * @return 主动检修工单总数
     */
    public Long selectTotal(String workOrderUnit) {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ACTIVE_REPAIR_ORDER.FAILURE_TIME.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        if (StringUtils.isNotBlank(workOrderUnit)) {
            queryWrapper.and(ACTIVE_REPAIR_ORDER.WORK_ORDER_UNIT.eq(workOrderUnit));
        }
        return activeRepairOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据状态查询主动检修工单数
     *
     * @param status        1已接单 2已完成 3未接单
     * @param workOrderUnit 工单单位
     * @return 主动检修工单数
     */
    public Long selectCountByStatus(Integer status, String workOrderUnit) {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (status == 1) {
            queryWrapper
                    .where(ACTIVE_REPAIR_ORDER.WORK_ORDER_STATUS.eq("已派单"))
                    .and(ACTIVE_REPAIR_ORDER.APP_STATUS.eq("已接单")
                            .or(ACTIVE_REPAIR_ORDER.APP_STATUS.eq("已推送"))
                    );
        } else if (status == 2) {
            queryWrapper.where(ACTIVE_REPAIR_ORDER.WORK_ORDER_STATUS.eq("已回复"));
        } else if (status == 3) {
            queryWrapper.where(ACTIVE_REPAIR_ORDER.WORK_ORDER_STATUS.eq("未受理"));
        } else {
            throw new RuntimeException("ActiveRepairOrder-SQL-status-error");
        }
        if (StringUtils.isNotBlank(workOrderUnit)) {
            queryWrapper.and(ACTIVE_REPAIR_ORDER.WORK_ORDER_UNIT.eq(workOrderUnit));
        }
        queryWrapper.and(ACTIVE_REPAIR_ORDER.FAILURE_TIME.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        return activeRepairOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询故障类型数量
     *
     * @param type 1:配变重载 2:配变过载
     * @return count
     */
    public Long selectFaultTypeCount(Integer type, LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (type == 1) {
            queryWrapper.where(ACTIVE_REPAIR_ORDER.FAULT_TYPE.eq("配变重载"));
        } else if (type == 2) {
            queryWrapper.where(ACTIVE_REPAIR_ORDER.FAULT_TYPE.eq("配变过载"));
        } else {
            throw new RuntimeException("selectFaultTypeCount-param error");
        }
        queryWrapper.and(ACTIVE_REPAIR_ORDER.FAILURE_TIME.between(beginDate, endDate));
        return activeRepairOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询单位根据类型
     *
     * @param faultType 故障类型
     * @return 配变重过载列表
     */
    public List<Row> selectWorkOrderUnitByType(
            String faultType, LocalDate beginDate, LocalDate endDate, String processing) {
        String sql = "SELECT DISTINCT WORK_ORDER_UNIT," +
                "     COUNT(1) OVER ( PARTITION BY WORK_ORDER_UNIT) COUNTS " +
                "     FROM ACTIVE_REPAIR_ORDER " +
                "     WHERE FAILURE_TIME BETWEEN ? AND ? ";
        if ("已处理".equals(processing)) {
            sql = sql + " AND PROCESSING_RESULT = '已处理' ";
        } else if ("未处理".equals(processing)) {
            sql = sql + " AND PROCESSING_RESULT = '未处理' ";
        }
        if ("重过载".equals(faultType)) {
            sql = sql + " AND (FAULT_TYPE = '配变重载' OR FAULT_TYPE = '配变过载') ";
            return Db.selectListBySql(sql, beginDate, endDate);
        } else {
            sql = sql + " AND FAULT_TYPE = ?";
            return Db.selectListBySql(sql, beginDate, endDate, faultType);
        }
    }

    /**
     * 查询处理结果根据类型
     *
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 处理结果
     */
    public List<Row> selectProcessingResultByType(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT PROCESSING_RESULT," +
                "     COUNT(1) OVER ( PARTITION BY PROCESSING_RESULT) COUNTS " +
                "     FROM ACTIVE_REPAIR_ORDER " +
                "     WHERE FAILURE_TIME BETWEEN ? AND ?";
        if ("重过载".equals(faultType)) {
            sql = sql + " AND (FAULT_TYPE = '配变重载' OR FAULT_TYPE = '配变过载') ";
            return Db.selectListBySql(sql, beginDate, endDate);
        } else {
            sql = sql + " AND FAULT_TYPE = ? ";
            return Db.selectListBySql(sql, beginDate, endDate, faultType);
        }
    }

}
