package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.ActiveRushRepairOrderDao;
import com.pg.screen.model.vo.XyDataCountVo;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 主动抢修工单业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class ActiveRushRepairOrderService {

    @Resource
    private ActiveRushRepairOrderDao activeRushRepairOrderDao;

    /**
     * 获取主动抢修工单总数
     *
     * @return 主动抢修工单总数
     */
    public HttpResult<Long> getTotal(String workOrderUnit) {
        Long count = activeRushRepairOrderDao.selectTotal(workOrderUnit);
        log.info("主动抢修工单总数：{}", count);
        return new HttpResult<Long>().success(count);
    }

    /**
     * 获取主动抢修工单状态XY轴数据
     *
     * @return 主动抢修工单状态XY轴数据
     */
    public HttpResult<XyDataCountVo> getCountByStatus(String workOrderUnit) {
        List<Row> rowList =
                activeRushRepairOrderDao.selectCountByStatus(workOrderUnit);
        log.info("主动抢修工单状态XY轴数据：{}", rowList);
        long notEntertained = 0L;
        long ordered = 0L;
        long replied = 0L;
        long filed = 0L;
        long counts;
        for (Row row : rowList) {
            String workOrderStatus = row.getString("WORK_ORDER_STATUS");
            counts = row.getLong("COUNTS", 0L);
            switch (workOrderStatus) {
                case "未受理":
                    notEntertained = counts;
                    break;
                case "已派单":
                    ordered = counts;
                    break;
                case "已回复":
                    replied = counts;
                    break;
                case "已归档":
                    filed = counts;
                    break;
            }
        }
        List<String> xAxisNameList = new LinkedList<>();
        xAxisNameList.add("未受理");
        xAxisNameList.add("已派单");
        xAxisNameList.add("已回复");
        xAxisNameList.add("已归档");
        Long finalNotEntertained = notEntertained;
        Long finalOrdered = ordered;
        Long finalReplied = replied;
        Long finalFiled = filed;
        List<Long> yAxisNameList = new LinkedList<>();
        yAxisNameList.add(finalNotEntertained);
        yAxisNameList.add(finalOrdered);
        yAxisNameList.add(finalReplied);
        yAxisNameList.add(finalFiled);
        XyDataCountVo vo =
                new XyDataCountVo(xAxisNameList, yAxisNameList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

}
