package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowUtil;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.FaultIndicatorMapper;
import com.pg.screen.model.vo.FaultIndicatorTypeVO;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.pg.screen.mapper.entity.table.FaultIndicatorTableDef.FAULT_INDICATOR;

/**
 * @author pengzhi.wang
 */
@Repository
public class FaultIndicatorDao {

    @Resource
    private FaultIndicatorMapper faultIndicatorMapper;


    /**
     * 根据地区查询故障指示器数量
     *
     * @param county 地区
     * @return 数量
     */
    public Long selectFaultIndicatorCount(String county) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(StringUtils.isNotBlank(county) ? FAULT_INDICATOR.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition());
        return faultIndicatorMapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据地区查询故障指示器类型数据
     *
     * @param county 地区
     * @return 故障指示器类型
     */
    public List<FaultIndicatorTypeVO> selectTypeCount(String county) {
        QueryWrapper queryWrapper = new QueryWrapper().select(
                        FAULT_INDICATOR.TYPE.as("typeName"),
                        count(FAULT_INDICATOR.ID).as("typeCount"))
                .from(FAULT_INDICATOR)
                .where(StringUtils.isNotBlank(county) ? FAULT_INDICATOR.OPERATION_AND_MAINTENANCE_UNIT.eq(WorkOrderUnitEnum.getFullName(county)) : noCondition())
                .groupBy(FAULT_INDICATOR.TYPE);
        List<Row> rows = Db.selectListByQuery(queryWrapper);
        return RowUtil.toObjectList(rows, FaultIndicatorTypeVO.class);
    }
}
