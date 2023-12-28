package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.*;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.entity.DefectLibrary;
import com.pg.screen.mapper.entity.DefectLibraryCountVo;
import com.pg.screen.mapper.entity.DefectLibraryInfoVo;
import com.pg.screen.model.vo.*;
import com.pg.screen.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 运维分析业务
 *
 * @author c.chuang
 */
@Slf4j
@Service
public class OmAnalyzeService {

    @Resource
    private ActiveRepairOrderDao activeRepairOrderDao;

    @Resource
    private TransformerMaximumLoadRatioDao transformerMaximumLoadRatioDao;

    @Resource
    private OperationMaintenanceOrderDao operationMaintenanceOrderDao;

    @Resource
    private InspectionHistoryControlDao inspectionHistoryControlDao;

    @Resource
    private DefectLibraryDao defectLibraryDao;

    /**
     * 获取配变重过载
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 配变重过载
     */
    public HttpResult<HeavyLoadOverloadVo> getHeavyLoadOverload(
            LocalDate beginDate, LocalDate endDate) {
        Long heavyLoadCount = activeRepairOrderDao
                .selectFaultTypeCount(1, beginDate, endDate);
        Long overloadCount = activeRepairOrderDao
                .selectFaultTypeCount(2, beginDate, endDate);
        HeavyLoadOverloadVo vo = new HeavyLoadOverloadVo();
        vo.setHeavyLoadCount(heavyLoadCount);
        vo.setOverloadCount(overloadCount);
        List<Row> heavyLoadList = activeRepairOrderDao.selectWorkOrderUnitByType(
                "配变重载", beginDate, endDate, null);
        List<Long> heavyLoadLong = ToolUtils.buildList(
                heavyLoadList, "WORK_ORDER_UNIT", "COUNTS");
        List<Row> overloadList = activeRepairOrderDao.selectWorkOrderUnitByType(
                "配变过载", beginDate, endDate, null);
        List<Long> overloadLong = ToolUtils.buildList(
                overloadList, "WORK_ORDER_UNIT", "COUNTS");
        vo.setXAxisNameList(WorkOrderUnitEnum.getShortName());
        vo.setYAxisNameList(heavyLoadLong);
        vo.setY2AxisNameList(overloadLong);
        return new HttpResult<HeavyLoadOverloadVo>().success(vo);
    }

    /**
     * 获取配变低电压和三相电流不平衡
     *
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<XyDataCountCountVo> getUnitByFaultType(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = activeRepairOrderDao.selectWorkOrderUnitByType(
                faultType, beginDate, endDate, null);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<XyDataCountCountVo>().success(null);
        }
        List<Long> list = ToolUtils.buildList(
                rows, "WORK_ORDER_UNIT", "COUNTS");
        long counts = rows.stream().mapToLong(
                v -> v.getLong("COUNTS", 0L)).sum();
        XyDataCountCountVo vo = new XyDataCountCountVo(
                WorkOrderUnitEnum.getShortName(), list, counts);
        return new HttpResult<XyDataCountCountVo>().success(vo);
    }

    /**
     * 异常台区治理情况
     *
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 处理结果
     */
    public HttpResult<ExceptionHandlingPieVo> getProcessingResultByType(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = activeRepairOrderDao
                .selectProcessingResultByType(faultType, beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<ExceptionHandlingPieVo>().success(null);
        }
        ExceptionHandlingPieVo vo = new ExceptionHandlingPieVo();
        List<ExceptionHandlingPieVo.EhpPieChart> pieChartList = new LinkedList<>();
        long counts = 0L;
        for (Row row : rows) {
            counts = counts + row.getLong("COUNTS", 0L);
            ExceptionHandlingPieVo.EhpPieChart pieChart =
                    new ExceptionHandlingPieVo.EhpPieChart();
            pieChart.setName(row.getString("PROCESSING_RESULT"));
            pieChart.setValue(row.getLong("COUNTS", 0L));
            pieChartList.add(pieChart);
        }
        vo.setPieChartList(pieChartList);
        vo.setCounts(counts);
        return new HttpResult<ExceptionHandlingPieVo>().success(vo);
    }

    /**
     * 获取异常台区处理情况-柱状图
     *
     * @param type      1:未处理 2:已处理 默认null/0
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 异常台区处理情况-柱状图
     */
    public HttpResult<XyDataCountVo> getExceptionHandlingForBar(
            Integer type, String faultType, LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = null;
        if (type == null || type == 0) {
            rows = activeRepairOrderDao.selectWorkOrderUnitByType(
                    faultType, beginDate, endDate, null);
        } else if (type == 1) {
            rows = activeRepairOrderDao.selectWorkOrderUnitByType(
                    faultType, beginDate, endDate, "未处理");
        } else if (type == 2) {
            rows = activeRepairOrderDao.selectWorkOrderUnitByType(
                    faultType, beginDate, endDate, "已处理");
        }
        //noinspection DuplicatedCode
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<XyDataCountVo>().success(null);
        }
        List<Long> list = ToolUtils.buildList(
                rows, "WORK_ORDER_UNIT", "COUNTS");
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), list);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取公变经济运行情况-数量和百分比
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 公变经济运行情况-数量和百分比
     */
    public HttpResult<EconomicOperationVo> getEconomicOperation(LocalDate beginDate, LocalDate endDate) {
        List<BigDecimal> rows = transformerMaximumLoadRatioDao.selectMaximumLoadRate(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<EconomicOperationVo>().success(null);
        }
        long zeroToTwenty = 0L;
        long twentyToFifty = 0L;
        long fiftyToSeventy = 0L;
        long seventyToHundred = 0L;
        long overHundred = 0L;
        long sum = 0L;
        for (BigDecimal maximumLoadRate : rows) {
            maximumLoadRate = maximumLoadRate.multiply(BigDecimal.valueOf(100));
            sum++;
            if (maximumLoadRate.compareTo(BigDecimal.valueOf(20)) < 1) {
                zeroToTwenty++;
            } else if (maximumLoadRate.compareTo(BigDecimal.valueOf(50)) < 1) {
                twentyToFifty++;
            } else if (maximumLoadRate.compareTo(BigDecimal.valueOf(70)) < 1) {
                fiftyToSeventy++;
            } else if (maximumLoadRate.compareTo(BigDecimal.valueOf(100)) < 1) {
                seventyToHundred++;
            } else if (maximumLoadRate.compareTo(BigDecimal.valueOf(101)) > -1) {
                overHundred++;
            }
        }
        EconomicOperationVo vo = new EconomicOperationVo();
        vo.setZeroToTwentyCount(zeroToTwenty);
        vo.setTwentyToFiftyCount(twentyToFifty);
        vo.setFiftyToSeventyCount(fiftyToSeventy);
        vo.setSeventyToHundredCount(seventyToHundred);
        vo.setOverHundredCount(overHundred);
        if (sum == 0) {
            sum = 1;
        }
        vo.setZeroToTwentyPercent(
                BigDecimal.valueOf(zeroToTwenty)
                        .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).intValue()
        );
        vo.setTwentyToFiftyPercent(
                BigDecimal.valueOf(twentyToFifty)
                        .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).intValue()
        );
        vo.setFiftyToSeventyPercent(
                BigDecimal.valueOf(fiftyToSeventy)
                        .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).intValue()
        );
        vo.setSeventyToHundredPercent(
                BigDecimal.valueOf(seventyToHundred)
                        .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).intValue()
        );
        vo.setOverHundredPercent(
                BigDecimal.valueOf(overHundred)
                        .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).intValue()
        );
        return new HttpResult<EconomicOperationVo>().success(vo);
    }

    /**
     * 获取公变经济运行情况-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 柱状图数据
     */
    public HttpResult<XyDataCountVo> getEconomicOperationUnitsCounts(LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = transformerMaximumLoadRatioDao.selectUnitsCounts(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<XyDataCountVo>().success(null);
        }
        List<Long> list = ToolUtils.buildList(rows, "UNITS", "COUNTS");
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), list);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    public HttpResult<XyDataCountVo> getEconomicOperationUnitsCounts(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        List<Row> rows = transformerMaximumLoadRatioDao.selectMaximumLoadRateUnit(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<XyDataCountVo>().success(null);
        }
        Map<String, List<Row>> unitsGroupList = rows.stream().collect(
                Collectors.groupingBy(v -> v.getString("UNITS")));
        BigDecimal maximumLoadRate;
        int count = 0;
        List<Row> resultList = new ArrayList<>();
        for (Map.Entry<String, List<Row>> entry : unitsGroupList.entrySet()) {
            Row row = new Row();
            row.put("UNITS", entry.getKey());
            count = 0;
            for (Row valueRow : entry.getValue()) {
                maximumLoadRate = valueRow.getBigDecimal("MAXIMUM_LOAD_RATE");
                maximumLoadRate = maximumLoadRate.multiply(BigDecimal.valueOf(100));
                if (type == 1 && maximumLoadRate.compareTo(BigDecimal.valueOf(20)) < 1) {
                    count++;
                } else if (type == 2 && maximumLoadRate.compareTo(BigDecimal.valueOf(50)) < 1) {
                    count++;
                } else if (type == 3 && maximumLoadRate.compareTo(BigDecimal.valueOf(70)) < 1) {
                    count++;
                } else if (type == 4 && maximumLoadRate.compareTo(BigDecimal.valueOf(100)) < 1) {
                    count++;
                } else if (type == 5 && maximumLoadRate.compareTo(BigDecimal.valueOf(101)) > -1) {
                    count++;
                }
            }
            row.put("COUNTS", count);
            resultList.add(row);
        }
        List<Long> list = ToolUtils.buildList(resultList, "UNITS", "COUNTS");
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), list);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取线路巡视统计数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 线路巡视统计数量
     */
    public HttpResult<LinePatrolStatisticsCountVo> getLinePatrolStatisticsCount(
            LocalDate beginDate, LocalDate endDate) {
        Long notInspectionCount = operationMaintenanceOrderDao
                .selectNotInspectionTourCount(beginDate, endDate);
        Long alreadyInspectionCount = inspectionHistoryControlDao
                .selectInspectionTour(beginDate, endDate);
        //noinspection DuplicatedCode
        long oughtToInspectionCount = notInspectionCount + alreadyInspectionCount;
        if (oughtToInspectionCount == 0) {
            oughtToInspectionCount = 1;
        }
        int alreadyInspectionPercent = BigDecimal.valueOf(alreadyInspectionCount)
                .divide(BigDecimal.valueOf(oughtToInspectionCount), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).intValue();
        int notInspectionPercent = BigDecimal.valueOf(notInspectionCount)
                .divide(BigDecimal.valueOf(oughtToInspectionCount), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).intValue();
        LinePatrolStatisticsCountVo vo = new LinePatrolStatisticsCountVo();
        vo.setNotInspectionCount(notInspectionCount);
        vo.setAlreadyInspectionCount(alreadyInspectionCount);
        vo.setNotInspectionPercent(notInspectionPercent);
        vo.setAlreadyInspectionPercent(alreadyInspectionPercent);
        vo.setOughtToInspectionCount(oughtToInspectionCount);
        return new HttpResult<LinePatrolStatisticsCountVo>().success(vo);
    }

    /**
     * 获取线路巡视统计数量-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0/null全部 1未巡视 2已巡视
     * @return 线路巡视统计数量
     */
    public HttpResult<XyDataCountVo> getLinePatrolStatisticsByUnit(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        List<Row> notInspectionList = null;
        List<Row> alreadyInspectionList = null;
        if (type == 1) {
            notInspectionList = operationMaintenanceOrderDao
                    .selectNotInspectionTourByUnit(beginDate, endDate);
        } else if (type == 2) {
            alreadyInspectionList = inspectionHistoryControlDao
                    .selectInspectionTourByUnit(beginDate, endDate);
        } else {
            notInspectionList = operationMaintenanceOrderDao
                    .selectNotInspectionTourByUnit(beginDate, endDate);
            alreadyInspectionList = inspectionHistoryControlDao
                    .selectInspectionTourByUnit(beginDate, endDate);
        }
        if (CollectionUtils.isEmpty(notInspectionList) && !CollectionUtils.isEmpty(alreadyInspectionList)) {
            List<Long> buildList = ToolUtils.buildList(
                    alreadyInspectionList, "WORK_ORDER_UNIT", "COUNTS");
            XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), buildList);
            return new HttpResult<XyDataCountVo>().success(vo);
        } else if (!CollectionUtils.isEmpty(notInspectionList) && CollectionUtils.isEmpty(alreadyInspectionList)) {
            List<Long> buildList = ToolUtils.buildList(
                    notInspectionList, "WORK_ORDER_UNIT", "COUNTS");
            XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), buildList);
            return new HttpResult<XyDataCountVo>().success(vo);
        } else {
            List<Long> buildListA = ToolUtils.buildList(
                    alreadyInspectionList, "WORK_ORDER_UNIT", "COUNTS");
            List<Long> buildListN = ToolUtils.buildList(
                    notInspectionList, "WORK_ORDER_UNIT", "COUNTS");
            List<Long> resultList = IntStream.range(0, buildListA.size()).mapToObj(
                    i -> buildListA.get(i) + buildListN.get(i)
            ).collect(Collectors.toList());
            XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), resultList);
            return new HttpResult<XyDataCountVo>().success(vo);
        }
    }

    /**
     * 获取线路巡视统计-饼图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<List<Row>> getInspectionTourByStatus(LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = operationMaintenanceOrderDao
                .selectInspectionTourByStatus(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<List<Row>>().success(null);
        }
        return new HttpResult<List<Row>>().success(rows);
    }


    /**
     * 获取缺陷处理统计数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public HttpResult<DefectLibraryCountVo> getWorkOrderStatusCount(LocalDate beginDate, LocalDate endDate) {
        Long notCount = defectLibraryDao
                .selectWorkOrderStatusCount(beginDate, endDate, "未消缺");
        Long alreadyCount = defectLibraryDao
                .selectWorkOrderStatusCount(beginDate, endDate, "已消缺");
        //noinspection DuplicatedCode
        int notPercent;
        int alreadyPercent;
        long sum = notCount + alreadyCount;
        if (sum != 0) {
            notPercent = BigDecimal.valueOf(notCount)
                    .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).intValue();
            alreadyPercent = BigDecimal.valueOf(alreadyCount)
                    .divide(BigDecimal.valueOf(sum), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).intValue();
        }else {
            notPercent = 0;
            alreadyPercent = 0;
        }
        DefectLibraryCountVo vo = new DefectLibraryCountVo();
        vo.setSum(sum);
        vo.setAlreadyCount(alreadyCount);
        vo.setNotCount(notCount);
        vo.setAlreadyPercent(alreadyPercent);
        vo.setNotPercent(notPercent);
        return new HttpResult<DefectLibraryCountVo>().success(vo);
    }

    /**
     * 获取缺陷处理统计-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0/null全部 1已消缺 2未消缺
     * @return list
     */
    public HttpResult<XyDataCountVo> getWorkOrderStatusByUnit(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        List<Row> rows = defectLibraryDao.selectWorkOrderStatusByUnit(beginDate, endDate, type);
        //noinspection DuplicatedCode
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<XyDataCountVo>().success(null);
        }
        List<Long> list = ToolUtils.buildList(rows, "WORK_ORDER_UNIT", "COUNTS");
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), list);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 缺陷模块数据统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<List<DefectLibraryInfoVo>> getWorkOrderInfo(String beginD, String endD) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginD=sdf.format(sdf.parse(beginD));
            endD=sdf.format(sdf.parse(endD));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate beginDate = LocalDate.parse(beginD, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(endD,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        endDate=endDate.plusDays(1);

        List<DefectLibraryInfoVo> defectLibraryInfoVos = new ArrayList<>();
        //按时间查询全部缺陷数
        List<DefectLibrary> defectLibraryList = defectLibraryDao.selectWorkOrderCount(beginDate, endDate);
        Map<String, List<DefectLibrary>> map = defectLibraryList.stream().collect(Collectors.groupingBy(DefectLibrary::getWorkOrderUnit));

        map.forEach((k,v) ->{

            DefectLibraryInfoVo defectLibraryInfoVo = new DefectLibraryInfoVo();
            //供电单位
            defectLibraryInfoVo.setName(k);
            log.info("供电单位："+k);
            //某单位缺陷数量
            int solveSum = v.size();
            defectLibraryInfoVo.setSolveSum(solveSum);
            log.info("发现缺陷数量："+solveSum);
            List<DefectLibrary> collect = v.stream().filter(o -> "已消缺".equals(o.getWorkOrderStatus())).collect(Collectors.toList());
            //某单位已消缺数量
            int completeSolve = collect.size();
            defectLibraryInfoVo.setCompleteSolve(completeSolve);
            log.info("已消缺数量："+completeSolve);
            //某单位缺陷处理率
            BigDecimal rate = new BigDecimal(completeSolve).divide (new BigDecimal(solveSum),2, BigDecimal.ROUND_HALF_UP);
            defectLibraryInfoVo.setRate(rate.multiply(BigDecimal.valueOf(100)));
            log.info("缺陷处理率："+defectLibraryInfoVo.getRate());
            //某单位平均时长
            AtomicReference<Long> dealTime = new AtomicReference<>(0L);
            collect.forEach(e ->{
                Date end = e.getProcessingTime();
                Date begin = e.getDiscoveryTime();
                Long time = end.getTime () -begin.getTime ();
                dealTime.updateAndGet(v1 -> v1 + time);
            });
            BigDecimal milliseconds = BigDecimal.valueOf(60 * 60 * 1000);
            BigDecimal bigDecimal = new BigDecimal (String.valueOf(dealTime));
            if(completeSolve==0){
                defectLibraryInfoVo.setAveDate("0小时");
            }else{
                BigDecimal millAve = bigDecimal.divide(BigDecimal.valueOf(completeSolve),2, BigDecimal.ROUND_HALF_UP);
                BigDecimal time = millAve.divide (milliseconds, BigDecimal.ROUND_UP);
                BigDecimal day = time.divide (new BigDecimal(24),0,BigDecimal.ROUND_FLOOR);
                BigDecimal hour = time.divideAndRemainder (new BigDecimal(24))[1].setScale(0,BigDecimal.ROUND_FLOOR);
                if(day.compareTo(BigDecimal.valueOf(0)) > 0 && hour.compareTo(BigDecimal.valueOf(0)) > 0){
                    defectLibraryInfoVo.setAveDate(day+"天"+hour+"小时");
                }else if(day.compareTo(BigDecimal.valueOf(0)) > 0 && hour.compareTo(BigDecimal.valueOf(0)) == 0){
                    defectLibraryInfoVo.setAveDate(day+"天");
                }else{
                    defectLibraryInfoVo.setAveDate(hour+"小时");
                }
            }
            log.info("缺陷处理平均时长："+defectLibraryInfoVo.getAveDate());

            defectLibraryInfoVos.add(defectLibraryInfoVo);
        });

        //按日期查询已巡检数
        List<Row> inspectionSumList = inspectionHistoryControlDao.selectinspectionSumByUnit(beginDate, endDate);

        inspectionSumList.forEach(e ->{
            for (int i = 0; i < defectLibraryInfoVos.size(); i++) {
                if(defectLibraryInfoVos.get(i).getName().equals(e.getString("WORK_ORDER_UNIT"))){
                    //某单位已巡检数量
                    defectLibraryInfoVos.get(i).setInspectionSum(Integer.parseInt(e.getString("COUNTS")));
                }else{
                    if(!map.containsKey(e.getString("WORK_ORDER_UNIT"))){
                        DefectLibraryInfoVo defectLibraryInfoVo = new DefectLibraryInfoVo();
                        defectLibraryInfoVo.setName(e.getString("WORK_ORDER_UNIT"));
                        defectLibraryInfoVo.setInspectionSum(Integer.parseInt(e.getString("COUNTS")));
                        defectLibraryInfoVo.setAveDate("0小时");
                        defectLibraryInfoVo.setRate(BigDecimal.valueOf(0));
                        defectLibraryInfoVos.add(defectLibraryInfoVo);
                        map.put(e.getString("WORK_ORDER_UNIT"),null);
                    }
                }
            }
        });

        return new HttpResult<List<DefectLibraryInfoVo>>().success(defectLibraryInfoVos);
    }

}
