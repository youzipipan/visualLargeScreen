package com.pg.screen.service;

import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.ActiveRepairOrderDao;
import com.pg.screen.model.vo.XyDataCountVo;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 主动检修工单业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class ActiveRepairOrderService {

    @Resource
    private ActiveRepairOrderDao activeRepairOrderDao;

    /**
     * 获取主动检修工单总数
     *
     * @return 主动检修工单总数
     */
    public HttpResult<Long> getTotal(String workOrderUnit) {
        Long count = activeRepairOrderDao.selectTotal(workOrderUnit);
        log.info("主动检修工单总数：{}", count);
        return new HttpResult<Long>().success(count);
    }

    /**
     * 获取主动检修工单状态XY轴数据
     *
     * @return 主动检修工单状态XY轴数据
     */
    public HttpResult<XyDataCountVo> getCountByStatus(String workOrderUnit) {
        // 已接单数量
        Long receivedOrderCount = activeRepairOrderDao.selectCountByStatus(1,workOrderUnit);
        log.info("主动检修-已接单数量：{}", receivedOrderCount);
        // 已完成数量
        Long finishedCount = activeRepairOrderDao.selectCountByStatus(2,workOrderUnit);
        log.info("主动检修-已完成数量：{}", finishedCount);
        List<Long> yAxisNameList = new LinkedList<>();
        yAxisNameList.add(0L);
        yAxisNameList.add(receivedOrderCount);
        yAxisNameList.add(finishedCount);
        List<String> xAxisNameList = new LinkedList<>();
        xAxisNameList.add("未接单");
        xAxisNameList.add("已接单");
        xAxisNameList.add("已完成");
        XyDataCountVo vo =
                new XyDataCountVo(xAxisNameList,yAxisNameList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

}
