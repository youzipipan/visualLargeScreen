package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.DefectLibraryMapper;
import com.pg.screen.mapper.entity.DefectLibrary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.pg.screen.mapper.entity.table.DefectLibraryTableDef.DEFECT_LIBRARY;

/**
 * 缺陷库 dao
 *
 * @author c.chuang
 */
@Repository
public class DefectLibraryDao {

    @Resource
    private DefectLibraryMapper defectLibraryMapper;

    /**
     * 查询缺陷库数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param status    工单状态
     * @return count
     */
    public Long selectWorkOrderStatusCount(LocalDate beginDate, LocalDate endDate, String status) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(DEFECT_LIBRARY.WORK_ORDER_STATUS.eq(status));
        if (beginDate != null && endDate != null) {
            queryWrapper.and(DEFECT_LIBRARY.PROCESSING_TIME.between(beginDate, endDate));
        }
        return defectLibraryMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询缺陷库根据单位
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectWorkOrderStatusByUnit(LocalDate beginDate, LocalDate endDate, Integer type) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(DEFECT_LIBRARY.WORK_ORDER_UNIT),
                        count().as("COUNTS"))
                .from(DEFECT_LIBRARY)
                .groupBy(DEFECT_LIBRARY.WORK_ORDER_UNIT);
        if (type == 1) {
            queryWrapper.where(DEFECT_LIBRARY.WORK_ORDER_STATUS.eq("已消缺"));
        } else if (type == 2) {
            queryWrapper.where(DEFECT_LIBRARY.WORK_ORDER_STATUS.eq("未消缺"));
        } else {
            queryWrapper.where(DEFECT_LIBRARY.WORK_ORDER_STATUS.eq("未消缺")
                    .or(DEFECT_LIBRARY.WORK_ORDER_STATUS.eq("已消缺")));
        }
        if (beginDate != null && endDate != null) {
            queryWrapper.and(DEFECT_LIBRARY.PROCESSING_TIME.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

    /**
     * 查询缺陷库数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public List<DefectLibrary> selectWorkOrderCount(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(DEFECT_LIBRARY.WORK_ORDER_UNIT,DEFECT_LIBRARY.WORK_ORDER_STATUS,DEFECT_LIBRARY.DISCOVERY_TIME,DEFECT_LIBRARY.PROCESSING_TIME)
                .where(DEFECT_LIBRARY.DISCOVERY_TIME.between(beginDate, endDate));
        return defectLibraryMapper.selectListByQuery(queryWrapper);
    }

}
