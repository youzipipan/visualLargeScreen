package com.pg.screen.dao;

import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.utils.TimeInterval;
import org.springframework.stereotype.Repository;

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
    public List<Row> selectWorkOrderDistributionByUnit(String tableName, String yearBegin, String yearEnd) {

        String where;
        if ("GZGD95598".equals(tableName)) {
            where = " WHERE SLSJ BETWEEN to_date('" + yearBegin + "','yyyy-MM-dd') " +
                    "AND to_date('" + yearEnd + "','yyyy-MM-dd')";
        } else if("INSPECTION_HISTORY_CONTROL".equals(tableName)){
            where = " WHERE INSPECTION_COMPLETION_TIME BETWEEN to_date('" + yearBegin + "','yyyy-MM-dd') " +
                    "AND to_date('" + yearEnd + "','yyyy-MM-dd')";
        } else {
            where = " WHERE FAILURE_TIME BETWEEN to_date('" + yearBegin + "','yyyy-MM-dd') " +
                    "AND to_date('" + yearEnd + "','yyyy-MM-dd')";
        }
        String sql;
        if("GZGD95598".equals(tableName)){
            sql = " SELECT " +
                    "DISTINCT GDDW AS WORK_ORDER_UNIT, " +
                    "COUNT(*) OVER (PARTITION BY GDDW) AS COUNTS " +
                    "FROM " + tableName + where;
        }else{
            sql = " SELECT " +
                    "DISTINCT WORK_ORDER_UNIT, " +
                    "COUNT(*) OVER (PARTITION BY WORK_ORDER_UNIT) AS COUNTS " +
                    "FROM " + tableName + where;
        }

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
