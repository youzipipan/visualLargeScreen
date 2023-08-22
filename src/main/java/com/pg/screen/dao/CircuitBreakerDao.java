package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.CircuitBreakerMapper;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.pg.screen.mapper.entity.table.CircuitBreakerTableDef.CIRCUIT_BREAKER;

/**
 * @author pengzhi.wang
 */
@Repository
public class CircuitBreakerDao {

    @Resource
    private CircuitBreakerMapper circuitBreakerMapper;

    /**
     * 根据地区查询断路器总数
     *
     * @param county 地区
     * @return 断路器总数量
     */
    public Long selectCircuitBreakerCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.eq("柱上断路器"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区断路器三遥数量
     *
     * @param county 地区
     * @return 三遥数量
     */
    public Long selectThreeRemoteCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.eq("柱上断路器"))
                .and(CIRCUIT_BREAKER.REPLACE_OR_NOT.eq("否"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区断路器普通数量
     *
     * @param county 地区
     * @return 普通数量
     */
    public Long selectNormalCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.eq("柱上断路器"))
                .and(CIRCUIT_BREAKER.REPLACE_OR_NOT.ne("否"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询断路器开关总数
     *
     * @param county 地区
     * @return 断路器开关总数量
     */
    public Long selectCircuitBreakerSwitchCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.ne("柱上断路器"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区开关三遥数量
     *
     * @param county 地区
     * @return 三遥数量
     */
    public Long selectThreeRemoteSwitchCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.ne("柱上断路器"))
                .and(CIRCUIT_BREAKER.REPLACE_OR_NOT.eq("否"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区断路器普通数量
     *
     * @param county 地区
     * @return 普通数量
     */
    public Long selectNormalSwitchCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CIRCUIT_BREAKER.DEVICE_TYPE_NAME.ne("柱上断路器"))
                .and(CIRCUIT_BREAKER.REPLACE_OR_NOT.ne("否"))
                .and(StringUtils.isNotBlank(county) ? CIRCUIT_BREAKER.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return circuitBreakerMapper.selectCountByQuery(queryWrapper);
    }
}
