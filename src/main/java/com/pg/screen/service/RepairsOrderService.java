package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.RepairsOrderDao;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.model.vo.RepairWorkOrderTypeAnalysisVo;
import com.pg.screen.model.vo.RushRepairAvgForUnitVo;
import com.pg.screen.model.vo.XyDataCountVo;
import com.pg.screen.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.util.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 报修工单业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class RepairsOrderService {

    @Resource
    private RepairsOrderDao repairsOrderDao;

    /**
     * 获取报修工单总数
     *
     * @param workerUnit 工单单位
     * @return 报修工单总数
     */
    public HttpResult<Long> getTotal(String workerUnit) {
        Long count = repairsOrderDao.selectTotal(workerUnit);
        log.info("报修工单总数：{}", count);
        return new HttpResult<Long>().success(count);
    }

    /**
     * 获取报修工单状态XY轴数据
     *
     * @return 报修工单状态XY轴数据
     */
    public HttpResult<XyDataCountVo> getCountByStatus(String workerUnit) {
        // 已接单数量
        Long receivedOrderCount = repairsOrderDao.selectCountByStatus(
                "已派单", "已接单", 1, workerUnit);
        log.info("报修状态工单数-已接单数量：{}", receivedOrderCount);
        // 抢修中数量
        Long repairCount = repairsOrderDao.selectCountByStatus(
                "已派单", "", 0, workerUnit);
        log.info("报修状态工单数-抢修中数量：{}", repairCount);
        // 已完成数量
        Long finishedCount = repairsOrderDao.selectCountByStatus(
                "已归档", "已完成 ", 2, workerUnit);
        log.info("报修状态工单数-已完成数量：{}", finishedCount);
        List<String> xAxisList = new LinkedList<>();
        xAxisList.add("未接单");
        xAxisList.add("已接单");
        xAxisList.add("抢修中");
        xAxisList.add("已完成");
        List<Long> yAxisList = new LinkedList<>();
        yAxisList.add(0L);
        yAxisList.add(receivedOrderCount);
        yAxisList.add(repairCount);
        yAxisList.add(finishedCount);
        XyDataCountVo vo = new XyDataCountVo(xAxisList, yAxisList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取报修工单类型分析一级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return list
     */
    public HttpResult<RepairWorkOrderTypeAnalysisVo> getRepairWorkOrderTypeAnalysis1(
            LocalDate beginDate, LocalDate endDate) {
        RepairWorkOrderTypeAnalysisVo vo = new RepairWorkOrderTypeAnalysisVo();
        List<Row> rowList = repairsOrderDao
                .selectRepairWorkOrderTypeAnalysis1(beginDate, endDate);
        long sum = rowList.stream().mapToLong(
                v -> v.getInt("COUNTS", 0)
        ).sum();
        vo.setTotal(sum);
        List<RepairWorkOrderTypeAnalysisVo.RepairWorkOrderTypeAnalysis> list =
                new LinkedList<>();
        BigDecimal counts;
        for (Row row : rowList) {
            RepairWorkOrderTypeAnalysisVo.RepairWorkOrderTypeAnalysis analysis =
                    new RepairWorkOrderTypeAnalysisVo.RepairWorkOrderTypeAnalysis();
            analysis.setName(row.getString("GZDL"));
            analysis.setValue(row.getBigDecimal("COUNTS"));
            list.add(analysis);
        }
        vo.setList(list);
        return new HttpResult<RepairWorkOrderTypeAnalysisVo>().success(vo);
    }


    /**
     * 获取报修工单类型分析二级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    public HttpResult<List<Row>> getRwotaSubclass2(
            LocalDate beginDate, LocalDate endDate, String type) {
        List<Row> rowList = repairsOrderDao
                .selectRepairWorkOrderTypeAnalysis2(beginDate, endDate, type);
        if (CollectionUtils.isEmpty(rowList)) {
            return new HttpResult<List<Row>>().success(null);
        }
        return new HttpResult<List<Row>>().success(rowList);
    }

    /**
     * 获取报修工单类型分析三级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    public HttpResult<List<Row>> getRwotaSubclass3(
            LocalDate beginDate, LocalDate endDate, String type) {
        List<Row> rowList = repairsOrderDao
                .selectRepairWorkOrderTypeAnalysis3(beginDate, endDate, type);
        if (CollectionUtils.isEmpty(rowList)) {
            return new HttpResult<List<Row>>().success(null);
        }
        return new HttpResult<List<Row>>().success(rowList);
    }

    /**
     * 获取抢修时长分析平均数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public HttpResult<Map<String, BigDecimal>> getRushRepairAvg(
            LocalDate beginDate, LocalDate endDate) {
        Double faultRepairAvg = repairsOrderDao
                .selectFaultRepairAvg(beginDate, endDate);
        Double arriveAtSceneAvg = repairsOrderDao
                .selectArriveAtSceneAvg(beginDate, endDate);
        Map<String, BigDecimal> resultMap = new HashMap<>(2);
        BigDecimal resultFaultRepairAvg = BigDecimal.valueOf(faultRepairAvg);
        BigDecimal resultArriveAtSceneAvg = BigDecimal.valueOf(arriveAtSceneAvg);
        resultMap.put("faultRepairAvg", resultFaultRepairAvg);
        resultMap.put("arriveAtSceneAvg", resultArriveAtSceneAvg);
        return new HttpResult<Map<String, BigDecimal>>().success(resultMap);
    }

    /**
     * 获取单位抢修时长分析
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public HttpResult<RushRepairAvgForUnitVo> getRushRepairAvgForUnit(
            LocalDate beginDate, LocalDate endDate) {
        RushRepairAvgForUnitVo vo = new RushRepairAvgForUnitVo();
        List<Row> faultRepairRowList = repairsOrderDao
                .selectFaultRepairAvgForUnit(beginDate, endDate);
        List<Double> yAxisValuesList = ToolUtils.buildListForDouble(
                faultRepairRowList, "GDDW", "AVG");
        List<BigDecimal> yAxisValuesResultList = new LinkedList<>();
        for (Double value : yAxisValuesList) {
            yAxisValuesResultList.add(
                    BigDecimal.valueOf(value)
            );
        }
        RushRepairAvgForUnitVo.RushRepairXyData faultRepairXy =
                new RushRepairAvgForUnitVo.RushRepairXyData(
                        WorkOrderUnitEnum.getShortName(), yAxisValuesResultList);
        vo.setFaultRepair(faultRepairXy);
        List<Row> arriveAtSceneRowList = repairsOrderDao
                .selectArriveAtSceneAvgForUnit(beginDate, endDate);
        yAxisValuesList = ToolUtils.buildListForDouble(
                arriveAtSceneRowList, "GDDW", "AVG");
        yAxisValuesResultList.clear();
        for (Double value : yAxisValuesList) {
            yAxisValuesResultList.add(
                    BigDecimal.valueOf(value)
            );
        }
        RushRepairAvgForUnitVo.RushRepairXyData arriveAtSceneXy =
                new RushRepairAvgForUnitVo.RushRepairXyData(
                        WorkOrderUnitEnum.getShortName(), yAxisValuesResultList);
        vo.setArriveAtScene(arriveAtSceneXy);
        return new HttpResult<RushRepairAvgForUnitVo>().success(vo);
    }

    /**
     * 获取报修工单数量分析-月
     *
     * @return 报修工单数量分析-月
     */
    public HttpResult<Map<Integer, List<Long>>> getRepairQuantityMonth() {
        final LocalDate now = LocalDate.now();
        final LocalDate prev = now.minusYears(1);
        final Map<Integer, List<Long>> resultMap = new HashMap<>();
        resultMap.put(prev.getYear(), handleYearRow(prev));
        resultMap.put(now.getYear(), handleYearRow(now));
        return new HttpResult<Map<Integer, List<Long>>>().success(resultMap);
    }

    private List<Long> handleYearRow(LocalDate date) {
        final List<Long> resultList = new LinkedList<>();
        final Row yearRow = repairsOrderDao.selectRepairQuantityMonth(date);
        for (int i = 1; i <= 12; i++) {
            resultList.add(yearRow.getLong("M" + i));
        }
        return resultList;
    }

    /**
     * 获取报修工单数量分析-小时
     *
     * @param date 日期
     * @return 报修工单数量分析-小时
     */
    public HttpResult<List<Long>> getRepairQuantityHour(String date) {
        List<Long> resultList = new LinkedList<>();
        Row row = repairsOrderDao.selectRepairQuantityHour(date);
        for (int i = 1; i <= 24; i++) {
            resultList.add(row.getLong("H" + i));
        }
        return new HttpResult<List<Long>>().success(resultList);
    }

    /**
     * 获取配网抢修工单分布
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 配网抢修工单分布
     */
    public HttpResult<XyDataCountVo> getWorkOrderDistribution(
            LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = repairsOrderDao
                .selectWorkOrderDistribution(beginDate, endDate);
        List<Long> reslutList = ToolUtils.buildList(rowList, "GDDW", "COUNTS");
        XyDataCountVo vo = new XyDataCountVo(
                WorkOrderUnitEnum.getShortName(), reslutList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取配网抢修工单分布-抢修班组
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param unit      工单单位
     * @return 配网抢修工单分布
     */
    public HttpResult<List<Row>> getWorkOrderDistributionByUnit(
            LocalDate beginDate, LocalDate endDate, String unit) {
        List<Row> rowList = repairsOrderDao
                .selectWorkOrderDistributionByUnit(beginDate, endDate, unit);
        return new HttpResult<List<Row>>().success(rowList);
    }

    /**
     * 获取配网抢修工单分布-抢修人员
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param team      抢修班组
     * @return 配网抢修工单分布
     */
    public HttpResult<XyDataCountVo> getWorkOrderDistributionByUser(
            LocalDate beginDate, LocalDate endDate, String team, String company) {
        List<Row> rowList = repairsOrderDao
                .selectWorkOrderDistributionByUser(beginDate, endDate, team, company);
        List<String> xList = new LinkedList<>();
        List<Long> yList = new LinkedList<>();
        for (Row row : rowList) {
            xList.add(row.getString("QXRY"));
            yList.add(row.getLong("COUNTS"));
        }
        XyDataCountVo vo = new XyDataCountVo(xList, yList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取十万户报修率
     *
     * @return 十万户报修率
     */
    public HttpResult<BigDecimal> get10wRepairRate(LocalDate beginDate, LocalDate endDate) {
        Long count = repairsOrderDao.selectCount(beginDate, endDate);
        BigDecimal result = BigDecimal.valueOf(count).divide(
                BigDecimal.valueOf(42.433), 3, RoundingMode.HALF_UP);
        return new HttpResult<BigDecimal>().success(result);
    }

}
