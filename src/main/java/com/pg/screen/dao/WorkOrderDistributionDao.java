package com.pg.screen.dao;

import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.utils.TimeInterval;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

/**
 * 工单分布 dao
 *
 * @author c.chuang
 */
@Repository
public class WorkOrderDistributionDao {

    /**
     * 查询主动检修工单分布数量
     * 查询主动抢修工单分布数量
     * 查询报修工单分布数量
     * 查询运维工单分布数量
     *
     * @return 主动检修工单分布数量
     */
    public List<Row> selectWorkOrderDistributionByUnit(String tableName) {
        String where;
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        if ("OPERATION_MAINTENANCE_ORDER".equals(tableName)) {
            where = " WHERE INSPECTION_COMPLETION_TIME BETWEEN to_date('" + timeInterval.beginDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss') " +
                    "AND to_date('" + timeInterval.endDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss') ";
        } else {
            where = " WHERE COMPLETION_TIME BETWEEN to_date('" + timeInterval.beginDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss') " +
                    "AND to_date('" + timeInterval.endDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss')";
        }
        String sql = " SELECT " +
                "DISTINCT WORK_ORDER_UNIT, " +
                "COUNT(*) OVER (PARTITION BY WORK_ORDER_UNIT) AS COUNTS " +
                "FROM " + tableName + where;
        return Db.selectListBySql(sql);
    }

    /**
     * 查询报修工单分布数量
     *
     * @return 报修工单分布数量
     */
    public List<Row> selectRepairsOrderByUnit() {
        String sql = " SELECT " +
                "DISTINCT GDDW AS WORK_ORDER_UNIT, " +
                "COUNT(*) OVER (PARTITION BY GDDW) AS COUNTS " +
                "FROM GZGD95598 ";
        return Db.selectListBySql(sql);
    }

}
