package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.SwitchStationMapper;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.pg.screen.mapper.entity.table.SwitchStationTableDef.SWITCH_STATION;

/**
 * @author pengzhi.wang
 */
@Repository
public class SwitchStationDao {

    @Resource
    private SwitchStationMapper switchStationMapper;

    /**
     * 根据区域查询开关站内总数量
     *
     * @param county 地区
     * @return 总数量
     */
    public Long selectCountByCounty(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(StringUtils.isNotBlank(county) ? SWITCH_STATION.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return switchStationMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询开关站内三遥总数量
     *
     * @param county 地区
     * @return 三遥数量
     */
    public Long selectThreeRemoteCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper
                .where(StringUtils.isNotBlank(county) ? SWITCH_STATION.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition())
                .and(SWITCH_STATION.REPLACE_OR_NOT.eq("否"));
        return switchStationMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询开关站内普通总数量
     *
     * @param county 地区
     * @return 普通数量
     */
    public Long selectNormalCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper
                .where(StringUtils.isNotBlank(county) ? SWITCH_STATION.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition())
                .and(SWITCH_STATION.REPLACE_OR_NOT.ne("否"));
        return switchStationMapper.selectCountByQuery(queryWrapper);
    }

}
