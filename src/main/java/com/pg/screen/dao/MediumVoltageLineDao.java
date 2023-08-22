package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.pg.screen.mapper.MediumVoltageLineMapper;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Objects;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.mybatisflex.core.query.QueryMethods.sum;
import static com.pg.screen.mapper.entity.table.MediumVoltageLineTableDef.MEDIUM_VOLTAGE_LINE;

/**
 * @author pengzhi.wang
 */
@Repository
public class MediumVoltageLineDao {

    @Resource
    private MediumVoltageLineMapper mediumVoltageLineMapper;

    /**
     * 查询架空线路总数
     *
     * @param county 区域
     * @return 架空线路总计
     */
    public Long selectOverheadLineCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(1))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return mediumVoltageLineMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据区域查询架空线路总长
     *
     * @param county 区域
     * @return 架空线路总长
     */
    public BigDecimal selectOverheadLineTotal(String county) {
        QueryWrapper query = new QueryWrapper()
                .select(
                        sum(MEDIUM_VOLTAGE_LINE.LINE_TOTAL))
                .from(MEDIUM_VOLTAGE_LINE)
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(1))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageLineMapper.selectObjectByQuery(query)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageLineMapper.selectObjectByQuery(query);
    }

    /**
     * 根据地区查询电缆线路总条数
     *
     * @param county 地区
     * @return 电缆线路总条数
     */
    public Long selectCableLineCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(2))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return mediumVoltageLineMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询电缆线路总长
     *
     * @param county 地区
     * @return 电缆线路总长
     */
    public BigDecimal selectCableLineTotal(String county) {
        QueryWrapper query = new QueryWrapper()
                .select(
                        sum(MEDIUM_VOLTAGE_LINE.LINE_TOTAL))
                .from(MEDIUM_VOLTAGE_LINE)
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(2))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageLineMapper.selectObjectByQuery(query)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageLineMapper.selectObjectByQuery(query);
    }

    /**
     * 根据地区查询混合线路总条数
     *
     * @param county 地区
     * @return 混合线路总条数
     */
    public Long selectHybridLineCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(3))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return mediumVoltageLineMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询混合线路总长度
     *
     * @param county 地区
     * @return 混合线路总长
     */
    public BigDecimal selectHybridLineTotal(String county) {
        QueryWrapper query = new QueryWrapper()
                .select(
                        sum(MEDIUM_VOLTAGE_LINE.LINE_TOTAL))
                .from(MEDIUM_VOLTAGE_LINE)
                .where(MEDIUM_VOLTAGE_LINE.ERECTION_MODE.eq(3))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_LINE.COUNTY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageLineMapper.selectObjectByQuery(query)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageLineMapper.selectObjectByQuery(query);
    }
}
