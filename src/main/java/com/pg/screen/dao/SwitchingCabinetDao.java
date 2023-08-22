package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.SwitchingCabinetMapper;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.pg.screen.mapper.entity.table.SwitchingCabinetTableDef.SWITCHING_CABINET;

/**
 * @author pengzhi.wang
 */
@Repository
public class SwitchingCabinetDao {

    @Resource
    private SwitchingCabinetMapper switchingCabinetMapper;

    /**
     * 根据地区简称查询开关站数量
     *
     * @param county 地区
     * @return 开关站数量
     */
    public Long selectSwitchingCount(String county) {
        QueryWrapper query = QueryWrapper.create();
        query.where(StringUtils.isNotBlank(county) ? SWITCHING_CABINET.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition())
                .and(SWITCHING_CABINET.TYPE_OF_POWER_STATION.eq("开关站"));
        return switchingCabinetMapper.selectCountByQuery(query);
    }

    /**
     * 根据地区查询环网柜数量
     *
     * @param county 地区
     * @return 环网柜
     */
    public Long selectCabinetCount(String county) {
        QueryWrapper query = QueryWrapper.create();
        query.where(StringUtils.isNotBlank(county) ? SWITCHING_CABINET.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition())
                .and(SWITCHING_CABINET.TYPE_OF_POWER_STATION.eq("环网柜"));
        return switchingCabinetMapper.selectCountByQuery(query);
    }
}
