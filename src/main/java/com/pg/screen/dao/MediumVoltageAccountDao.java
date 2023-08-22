package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.pg.screen.mapper.MediumVoltageAccountMapper;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Objects;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.mybatisflex.core.query.QueryMethods.sum;
import static com.pg.screen.mapper.entity.table.MediumVoltageAccountTableDef.MEDIUM_VOLTAGE_ACCOUNT;

/**
 * @author pengzhi.wang
 */
@Repository
public class MediumVoltageAccountDao {

    @Resource
    private MediumVoltageAccountMapper mediumVoltageAccountMapper;

    /**
     * 根据区域查询配电变压器公变数量
     *
     * @param county 区域
     * @return 公变数量
     */
    public BigDecimal selectPublicTransformerCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(
                        sum(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_NUMBER))
                .from(MEDIUM_VOLTAGE_ACCOUNT)
                .where(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_TYPE.eq(1))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_ACCOUNT.CITY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper);
    }

    /**
     * 根据区域查询配电变压器公变容量
     *
     * @param county 地区
     * @return 公变容量
     */
    public BigDecimal selectPublicTransformerCapacity(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(
                        sum(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_CAPACITY))
                .from(MEDIUM_VOLTAGE_ACCOUNT)
                .where(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_TYPE.eq(1))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_ACCOUNT.CITY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper);

    }

    /**
     * 根据区域查询配电变压器专变数量
     *
     * @param county 区域
     * @return 专变数量
     */
    public BigDecimal selectSpecialTransformerCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(
                        sum(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_NUMBER))
                .from(MEDIUM_VOLTAGE_ACCOUNT)
                .where(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_TYPE.eq(2))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_ACCOUNT.CITY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper);
    }

    /**
     * 根据区域查询配电变压器专变容量
     *
     * @param county 区域
     * @return 专变容量
     */
    public BigDecimal selectSpecialTransformerCapacity(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(
                        sum(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_CAPACITY))
                .from(MEDIUM_VOLTAGE_ACCOUNT)
                .where(MEDIUM_VOLTAGE_ACCOUNT.TRANSFORMER_TYPE.eq(2))
                .and(StringUtils.isNotBlank(county) ? MEDIUM_VOLTAGE_ACCOUNT.CITY.eq(county) : noCondition());
        return Objects.isNull(mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper)) ? BigDecimal.ZERO : (BigDecimal) mediumVoltageAccountMapper.selectObjectByQuery(queryWrapper);
    }
}
