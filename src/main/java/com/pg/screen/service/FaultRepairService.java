package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.FaultRepairDao;
import com.pg.screen.mapper.entity.FaultRepair;
import com.pg.screen.model.vo.FaultRepairCountVO;
import com.pg.screen.model.vo.FaultRepairFlagCountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 故障——主动抢修 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-07-16
 */
@Service
@Slf4j
public class FaultRepairService {

    @Resource
    private FaultRepairDao faultRepairDao;

    public HttpResult<FaultRepairCountVO> findCount() {
        return new HttpResult<FaultRepairCountVO>().success(faultRepairDao.findRepairCount());
    }

    public HttpResult<List<FaultRepairFlagCountVO>> findFaultRepairFlagCount(String type) {
        final List<Row> rowList = faultRepairDao.findFaultRepairFlagCount(type);
        return new HttpResult<List<FaultRepairFlagCountVO>>().success(
                rowList.stream().map(row -> {
                    final FaultRepairFlagCountVO flagCountVO = new FaultRepairFlagCountVO();
                    flagCountVO.setFlag(row.getString("FLAG"));
                    flagCountVO.setCount(row.getString("COUNT"));
                    return flagCountVO;
                }).collect(Collectors.toList())
        );
    }

    public HttpResult<List<FaultRepair>> selectListData(String flag, String type) {
        final List<Row> rowList = faultRepairDao.selectList(flag, type);
        return new HttpResult<List<FaultRepair>>().success(
                rowList.stream().map(row -> {
                    final FaultRepair faultRepair = new FaultRepair();
                    faultRepair.setGddw(row.getString("GDDW"));
                    faultRepair.setGzfssj(row.getLocalDateTime("GZFSSJ"));
                    faultRepair.setLxr(row.getString("LXR"));
                    faultRepair.setLxdh(row.getLong("LXDH"));
                    faultRepair.setGzdz(row.getString("GZDZ"));
                    faultRepair.setJdsj(row.getLocalDateTime("JDSJ"));
                    faultRepair.setDdxcsj(row.getLocalDateTime("DDXCSJ"));
                    faultRepair.setQxwcsj(row.getLocalDateTime("QXWCSJ"));
                    faultRepair.setQxjl(row.getString("QXJL"));
                    return faultRepair;
                }).collect(Collectors.toList())
        );
    }
}
