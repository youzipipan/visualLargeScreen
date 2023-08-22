package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.TransformerMaximumLoadRatioMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.pg.screen.mapper.entity.table.TransformerMaximumLoadRatioTableDef.TRANSFORMER_MAXIMUM_LOAD_RATIO;

/**
 * 变压器最大负载率计算 dao
 *
 * @author c.chuang
 */
@Repository
public class TransformerMaximumLoadRatioDao {

    @Resource
    private TransformerMaximumLoadRatioMapper transformerMaximumLoadRatioMapper;

    /**
     * 查询最大负载率
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 最大负载率
     */
    public List<BigDecimal> selectMaximumLoadRate(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(TRANSFORMER_MAXIMUM_LOAD_RATIO.MAXIMUM_LOAD_RATE);
        if (beginDate != null && endDate != null) {
            queryWrapper.where(TRANSFORMER_MAXIMUM_LOAD_RATIO.CREATE_TIME.between(beginDate, endDate));
        }
        return transformerMaximumLoadRatioMapper.selectListByQueryAs(queryWrapper, BigDecimal.class);
    }

    public List<Row> selectMaximumLoadRateUnit(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(TRANSFORMER_MAXIMUM_LOAD_RATIO.MAXIMUM_LOAD_RATE,
                        TRANSFORMER_MAXIMUM_LOAD_RATIO.UNITS)
                .from(TRANSFORMER_MAXIMUM_LOAD_RATIO);
        if (beginDate != null && endDate != null) {
            queryWrapper.where(TRANSFORMER_MAXIMUM_LOAD_RATIO.CREATE_TIME.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

    /**
     * 变压器最大负载率计算的单位和Count
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 单位和Count
     */
    public List<Row> selectUnitsCounts(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(TRANSFORMER_MAXIMUM_LOAD_RATIO.UNITS), count().as("COUNTS"))
                .from(TRANSFORMER_MAXIMUM_LOAD_RATIO);
        if (beginDate != null && endDate != null) {
            queryWrapper.where(TRANSFORMER_MAXIMUM_LOAD_RATIO.CREATE_TIME.between(beginDate, endDate));
        }
        queryWrapper.groupBy(TRANSFORMER_MAXIMUM_LOAD_RATIO.UNITS);
        return Db.selectListByQuery(queryWrapper);
    }

}
