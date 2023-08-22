package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.InspectionHistoryControlMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.pg.screen.mapper.entity.table.InspectionHistoryControlTableDef.INSPECTION_HISTORY_CONTROL;

/**
 * 巡视历史管控 dao
 *
 * @author c.chuang
 */
@Repository
public class InspectionHistoryControlDao {

    @Resource
    private InspectionHistoryControlMapper inspectionHistoryControlMapper;

    /**
     * 查询已巡视数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public Long selectInspectionTour(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(INSPECTION_HISTORY_CONTROL.INSPECTION_COMPLETION_TIME.isNotNull());
        if (beginDate != null && endDate != null) {
            queryWrapper.and(INSPECTION_HISTORY_CONTROL.TIME_OF_ARRIVAL.between(beginDate, endDate));
        }
        return inspectionHistoryControlMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询已巡视数量根据单位
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectInspectionTourByUnit(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(INSPECTION_HISTORY_CONTROL.WORK_ORDER_UNIT),
                        count().as("COUNTS"))
                .from(INSPECTION_HISTORY_CONTROL)
                .where(INSPECTION_HISTORY_CONTROL.INSPECTION_COMPLETION_TIME.isNotNull())
                .groupBy(INSPECTION_HISTORY_CONTROL.WORK_ORDER_UNIT);
        if (beginDate != null && endDate != null) {
            queryWrapper.and(INSPECTION_HISTORY_CONTROL.TIME_OF_ARRIVAL.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

}
