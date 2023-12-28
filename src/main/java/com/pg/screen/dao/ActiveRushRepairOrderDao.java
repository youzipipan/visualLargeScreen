package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.ActiveRushRepairOrderMapper;

import javax.annotation.Resource;

import com.pg.screen.utils.TimeInterval;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import static com.pg.screen.mapper.entity.table.ActiveRushRepairOrderTableDef.ACTIVE_RUSH_REPAIR_ORDER;

/**
 * 主动抢修 dao
 *
 * @author c.chuang
 */
@Repository
public class ActiveRushRepairOrderDao {

    @Resource
    private ActiveRushRepairOrderMapper activeRushRepairOrderMapper;

    /**
     * 查询主动抢修总数
     *
     * @param workOrderUnit 工单单位
     * @return 主动抢修总数
     */
    public Long selectTotal(String workOrderUnit) {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ACTIVE_RUSH_REPAIR_ORDER.FAILURE_TIME.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        if (StringUtils.isNotBlank(workOrderUnit)) {
            queryWrapper.and(ACTIVE_RUSH_REPAIR_ORDER.WORK_ORDER_UNIT.eq(workOrderUnit));
        }
        return activeRushRepairOrderMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据工单状态查询主动抢修行数
     *
     * @param workOrderUnit 工单单位
     * @return 主动抢修行数
     */
    public List<Row> selectCountByStatus(String workOrderUnit) {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        String sql = "SELECT DISTINCT WORK_ORDER_STATUS," +
                "            COUNT(*) OVER (PARTITION BY WORK_ORDER_STATUS) AS COUNTS " +
                "     FROM ACTIVE_RUSH_REPAIR_ORDER " +
                "     WHERE WORK_ORDER_STATUS IN (?,?,?,?) " +
                "           AND FAILURE_TIME BETWEEN to_date('" + timeInterval.beginDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss') " +
                "           AND to_date('" + timeInterval.endDateFormat("yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd HH24:mi:ss') ";
        if (StringUtils.isNotBlank(workOrderUnit)) {
            sql = sql + " AND WORK_ORDER_UNIT = ?";
            return Db.selectListBySql(
                    sql, "未受理", "已派单", "已回复", "已归档", workOrderUnit);
        }
        return Db.selectListBySql(
                sql, "未受理", "已派单", "已回复", "已归档");
    }

}
