package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.FaultDetailDao;
import com.pg.screen.dao.OperationMaintenanceOrderDao;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.mapper.GddwXlcdMapper;
import com.pg.screen.mapper.dto.GddwXlcd;
import com.pg.screen.model.vo.*;
import com.pg.screen.utils.ToolUtils;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.pg.screen.utils.ToolUtils.buildList;

/**
 * 故障明细业务
 *
 * @author c.chuang
 */
@Service
@Slf4j
public class FaultDetailService {

    @Resource
    private FaultDetailDao faultDetailDao;

    @Resource
    private OperationMaintenanceOrderDao operationMaintenanceOrderDao;

    @Autowired
    private GddwXlcdMapper gddwXlcdMapper;

    /**
     * 线路故障分析-获取故障数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障数量
     */
    public HttpResult<Long> getTotal(LocalDate beginDate, LocalDate endDate) {
        Long count = faultDetailDao.selectTotal(beginDate, endDate);
        log.info("线路故障分析-故障数量：{}", count);
        return new HttpResult<Long>().success(count);
    }

    /**
     * 线路故障分析-根据故障类型获取故障数量
     *
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public HttpResult<XyDataCountVo> getLineFaultAnalysisCount(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao
                .selectLineFaultAnalysisCount(faultType, beginDate, endDate);
        List<Long> yAxisValuesList = buildList(
                rowList, "COUNTY_CORPORATION", "COUNTS");
        log.info("线路故障分析-根据故障类型获取故障数量：{}", yAxisValuesList);
        XyDataCountVo vo = new XyDataCountVo(
                WorkOrderUnitEnum.getShortName(), yAxisValuesList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 线路故障分析-根据支线故障获取故障数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public HttpResult<Xy2DataCountVo> getLineFaultAnalysisCountByBranch(
            LocalDate beginDate, LocalDate endDate) {
        List<Row> row1List = faultDetailDao.selectLineFaultAnalysisCount(
                "分支线停电(自动跳闸)", beginDate, endDate);
        List<Long> yAxisValuesList = buildList(
                row1List, "COUNTY_CORPORATION", "COUNTS");
        List<Row> row2List = faultDetailDao.selectLineFaultAnalysisCount(
                "分支线停电(主动拉闸)", beginDate, endDate);
        List<Long> y2AxisValuesList = buildList(
                row2List, "COUNTY_CORPORATION", "COUNTS");
        log.info("线路故障分析-根据故障类型获取故障数量：{}", yAxisValuesList);
        Xy2DataCountVo vo = new Xy2DataCountVo(
                WorkOrderUnitEnum.getShortName(), yAxisValuesList, y2AxisValuesList);
        return new HttpResult<Xy2DataCountVo>().success(vo);
    }

    /**
     * 线路故障分析-获得故障类型的数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障类型的数量
     */
    public HttpResult<FaultTypeCountVo> getCountByFaultType(
            LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao.selectCountByFaultType(beginDate, endDate);
        FaultTypeCountVo vo = new FaultTypeCountVo();
        Long branchFaultInitiativeCount = 0L;
        Long branchFaultPassivityCount = 0L;
        for (Row row : rowList) {
            String key = row.getString("FAULT_TYPE");
            switch (key) {
                case "重合不良":
                    vo.setMisalignmentCount(
                            row.getLong("COUNTS", 0L));
                    break;
                case "重合良好":
                    vo.setGoodCoincideCount(
                            row.getLong("COUNTS", 0L));
                    break;
                case "分支线停电(主动拉闸)":
                    branchFaultInitiativeCount =
                            row.getLong("COUNTS", 0L);
                    break;
                case "接地故障":
                    vo.setGroundFaultCount(
                            row.getLong("COUNTS", 0L));
                    break;
                case "分支线停电(自动跳闸)":
                    branchFaultPassivityCount =
                            row.getLong("COUNTS", 0L);
                    break;
            }
        }
        vo.setBranchFault(branchFaultInitiativeCount + branchFaultPassivityCount);
        return new HttpResult<FaultTypeCountVo>().success(vo);
    }

    /**
     * 线路故障修复时长-获取线路故障平均修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 线路故障平均修复时长
     */
    public HttpResult<BigDecimal> getLineFaultRepairDurationAvgTotal(LocalDate beginDate, LocalDate endDate) {
        Row row = faultDetailDao.selectLineFaultRepairDurationAvgTotal(beginDate, endDate);
        if (row == null) {
            return new HttpResult<BigDecimal>().success(BigDecimal.ZERO);
        }
        Long sum = row.getLong("SUM", 0L);
        Long count = faultDetailDao.selectTotal(beginDate, endDate);
        if (count == 0) {
            count = 1L;
        }
        BigDecimal avg = BigDecimal.valueOf(sum).divide(
                BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        return new HttpResult<BigDecimal>().success(avg);
    }

    /**
     * 线路故障修复时长-获取故障修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障修复时长
     */
    public HttpResult<XyDataCountVo> getLineFaultRepairDurationAvg(LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao.selectLineFaultRepairDurationAvg(beginDate, endDate);
        List<Long> yAxisValuesList = buildList(
                rowList, "COUNTY_CORPORATION", "AVG");
        log.info("线路故障修复时长-查询故障修复时长：{}", yAxisValuesList);
        XyDataCountVo vo = new XyDataCountVo(
                WorkOrderUnitEnum.getShortName(), yAxisValuesList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 线路故障修复时长-获取影响用户数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 影响用户数
     */
    public HttpResult<XyDataCountVo> getAffectUsersNumber(LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao.selectAffectUsersNumber(beginDate, endDate);
        List<Long> yAxisValuesList = buildList(rowList, "COUNTY_CORPORATION", "COUNTS");
        log.info("线路故障修复时长-获取影响用户数：{}", yAxisValuesList);
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), yAxisValuesList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 线路故障修复时长-获取超8小时故障
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 超8小时故障
     */
    public HttpResult<XyDataCountVo> get8HourFailure(LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao.select8HourFailure(beginDate, endDate);
        List<Long> yAxisValuesList = buildList(rowList, "COUNTY_CORPORATION", "COUNTS");
        log.info("线路故障修复时长-查询超8小时故障：{}", yAxisValuesList);
        XyDataCountVo vo = new XyDataCountVo(WorkOrderUnitEnum.getShortName(), yAxisValuesList);
        return new HttpResult<XyDataCountVo>().success(vo);
    }

    /**
     * 获取频繁故障线路数据
     *
     * @param beginMonth 开始月
     * @param endMonth   结束月
     * @param year       年
     * @param type       1：月度累计 2：年度累计
     * @return 频繁故障线路-月
     */
    public HttpResult<FrequentFailureVo> getFrequentFailure(
            String beginMonth, String endMonth, String year, Integer type) {
        List<Row> monthList = faultDetailDao.selectFrequentFailureMonth(beginMonth, endMonth);
        if (monthList == null) {
            monthList = new ArrayList<>();
        }
        List<Row> monthFilterList = monthList.stream().filter(
                v -> v.getLong("COUNTS") > 3).collect(Collectors.toList());
        int monthSum = monthFilterList.stream().mapToInt(
                v -> v.getInt("COUNTS", 0)
        ).sum();
        List<Row> yearList = faultDetailDao.selectFrequentFailureYear(year);
        if (yearList == null) {
            yearList = new ArrayList<>();
        }
        List<Row> yearFilterList = yearList.stream().filter(
                v -> v.getLong("COUNTS") > 5).collect(Collectors.toList());
        int yearSum = yearFilterList.stream().mapToInt(
                v -> v.getInt("COUNTS", 0)
        ).sum();
        FrequentFailureVo vo = new FrequentFailureVo();
        vo.setMonthTotal(monthSum);
        vo.setMonthNum(monthFilterList.size());
        vo.setYearTotal(yearSum);
        vo.setYearNum(yearFilterList.size());
        List<FrequentFailureVo.FrequentFailure> list = new LinkedList<>();
        List<Row> resultList;
        if (type == 1) {
            resultList = monthFilterList;
        } else {
            resultList = yearFilterList;
        }
        for (Row row : resultList) {
            if (row.getLong("COUNTS", 0L) == 0) {
                continue;
            }
            FrequentFailureVo.FrequentFailure entity =
                    row.toEntity(FrequentFailureVo.FrequentFailure.class);
            list.add(entity);
        }
        vo.setList(list);
        return new HttpResult<FrequentFailureVo>().success(vo);
    }

    /**
     * 获取线路故障率排名
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<XyDataBigDecimalVo> getLineFailureRateRanking(
            LocalDate beginDate, LocalDate endDate) {
        List<Row> rowList = faultDetailDao
                .selectLineFailureRateRanking(beginDate, endDate);
        String name;
        BigDecimal value;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal total_long = BigDecimal.ZERO;
        int count = 1;
        Map<String, BigDecimal> map = new HashMap<>();
        for (Row row : rowList) {
            name = row.getString("COUNTY_CORPORATION");
            value = row.getBigDecimal("COUNTS", BigDecimal.ZERO);
            count++;
            total = total.add(value);
            switch (name) {
                case "中山供电分公司":
                    map.put(
                            "中山", value.divide(getLineLength().get("中山供电分公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("中山供电分公司"));
                    break;
                case "沙河口供电分公司":
                    map.put(
                            "沙河口", value.divide(getLineLength().get("沙河口供电分公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("沙河口供电分公司"));
                    break;
                case "甘井子供电分公司":
                    map.put(
                            "甘井子", value.divide(getLineLength().get("甘井子供电分公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("甘井子供电分公司"));
                    break;
                case "高新园区供电分公司":
                    map.put(
                            "高新", value.divide(getLineLength().get("高新园区供电分公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("高新园区供电分公司"));
                    break;
                case "国网大连市金州新区供电公司":
                    map.put(
                            "金州", value.divide(getLineLength().get("国网大连市金州新区供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网大连市金州新区供电公司"));
                    break;
                case "国网大连市开发区东部供电公司":
                    map.put(
                            "开东", value.divide(getLineLength().get("国网大连市开发区东部供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网大连市开发区东部供电公司"));
                    break;
                case "国网大连市开发区供电公司":
                    map.put(
                            "开发区", value.divide(getLineLength().get("国网大连市开发区供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网大连市开发区供电公司"));
                    break;
                case "国网大连市旅顺口区供电公司":
                    map.put(
                            "旅顺", value.divide(getLineLength().get("国网大连市旅顺口区供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网大连市旅顺口区供电公司"));
                    break;
                case "国网瓦房店市供电公司":
                    map.put(
                            "瓦房店", value.divide(getLineLength().get("国网瓦房店市供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网瓦房店市供电公司"));
                    break;
                case "国网普兰店市供电公司":
                    map.put(
                            "普兰店", value.divide(getLineLength().get("国网普兰店市供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网普兰店市供电公司"));
                    break;
                case "国网庄河市供电公司":
                    map.put(
                            "庄河", value.divide(getLineLength().get("国网庄河市供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网庄河市供电公司"));
                    break;
                case "国网长海县供电公司":
                    map.put(
                            "长海", value.divide(getLineLength().get("国网长海县供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网长海县供电公司"));
                    break;
                case "国网大连市长兴岛临港工业区供电公司":
                    map.put(
                            "长兴岛", value.divide(getLineLength().get("国网大连市长兴岛临港工业区供电公司"), 4, RoundingMode.HALF_UP));
                    total_long = total_long.add(getLineLength().get("国网大连市长兴岛临港工业区供电公司"));
                    break;
            }
        }
        List<BigDecimal> yAxisValuesList = new LinkedList<>();
        for (String sName : WorkOrderUnitEnum.getShortName()) {
            boolean flag = false;
            for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
                if (StringUtils.equals(sName, entry.getKey())) {
                    yAxisValuesList.add(entry.getValue());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                yAxisValuesList.add(BigDecimal.ZERO);
            }
        }
        XyDataBigDecimalVo vo = new XyDataBigDecimalVo(
                WorkOrderUnitEnum.getShortName(), yAxisValuesList);
//        vo.setTotal(total.divide(BigDecimal.valueOf(count), 4, RoundingMode.HALF_UP));
        //2023-11-06
        vo.setTotal(total.divide(total_long, 4, RoundingMode.HALF_UP));
        //总次数，除以总长度修改
        return new HttpResult<XyDataBigDecimalVo>().success(vo);
    }

    private Map<String, BigDecimal> getLineLength() {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        List<GddwXlcd> gddwXlcdList=gddwXlcdMapper.getpage();
        for(GddwXlcd item:gddwXlcdList){
            resultMap.put(item.getGddw(), BigDecimal.valueOf(Long.parseLong(item.getXlcd())));
        }
//        resultMap.put("zs", BigDecimal.valueOf(10.59828));
//        resultMap.put("shk", BigDecimal.valueOf(8.27639));
//        resultMap.put("gjz", BigDecimal.valueOf(18.81839));
//        resultMap.put("gx", BigDecimal.valueOf(21.4854));
//        resultMap.put("jz", BigDecimal.valueOf(39.6035));
//        resultMap.put("ls", BigDecimal.valueOf(18.22568));
//        resultMap.put("wfd", BigDecimal.valueOf(42.03));
//        resultMap.put("pld", BigDecimal.valueOf(33.5168));
//        resultMap.put("zh", BigDecimal.valueOf(41.26));
//        resultMap.put("kfq", BigDecimal.valueOf(9.12875));
//        resultMap.put("kd", BigDecimal.valueOf(16.5553));
//        resultMap.put("cxd", BigDecimal.valueOf(9.3558));
//        resultMap.put("ch", BigDecimal.valueOf(5.343834));
        return resultMap;
    }

    /**
     * 获取线路故障成因分析
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<LineFailureCauseAnalysisVo> getLineFailureCauseAnalysis(
            LocalDate beginDate, LocalDate endDate) {
        LineFailureCauseAnalysisVo vo = new LineFailureCauseAnalysisVo();
        List<Row> rowList = faultDetailDao
                .selectLineFailureCauseAnalysis(beginDate, endDate);
        if (CollectionUtils.isEmpty(rowList)) {
            return new HttpResult<LineFailureCauseAnalysisVo>().success(new LineFailureCauseAnalysisVo());
        }
        List<LineFailureCauseAnalysisVo.LineFailureCauseAnalysis> resultList = new LinkedList<>();
        BigDecimal sum = BigDecimal.ZERO;
        int counts = rowList.stream().mapToInt(
                v -> v.getInt("COUNTS", 0)).sum();
        for (Row row : rowList) {
            LineFailureCauseAnalysisVo.LineFailureCauseAnalysis lineFailureCauseAnalysis =
                    new LineFailureCauseAnalysisVo.LineFailureCauseAnalysis();
            lineFailureCauseAnalysis.setFaultCause(row.getString("FAULT_CAUSE"));
            BigDecimal rowValue = row.getBigDecimal("COUNTS", BigDecimal.ZERO);
            sum = sum.add(rowValue);
            BigDecimal percentValue;
            if (counts == 0) {
                percentValue = rowValue.divide(
                        BigDecimal.ONE,
                        2,
                        RoundingMode.HALF_UP);
            } else {
                percentValue = rowValue.divide(
                        BigDecimal.valueOf(counts),
                        2,
                        RoundingMode.HALF_UP);
            }
            lineFailureCauseAnalysis.setPercentValue(
                    percentValue.multiply(BigDecimal.valueOf(100)));
            resultList.add(lineFailureCauseAnalysis);
        }
        vo.setTotal(sum.longValue());
        vo.setList(resultList);
        return new HttpResult<LineFailureCauseAnalysisVo>().success(vo);
    }

    /**
     * 获取线路故障成因分析-根据故障原因
     *
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @param faultCause 故障原因
     * @return list
     */
    public HttpResult<List<Row>> getLineFailureCauseAnalysisByFaultCause(
            LocalDate beginDate, LocalDate endDate, String faultCause) {
        List<Row> rowList = faultDetailDao.selectLineFailureCauseAnalysisByFaultCause(
                beginDate, endDate, faultCause);
        if (CollectionUtils.isEmpty(rowList)) {
            //List<Row> list = new ArrayList<>();
            return new HttpResult<List<Row>>().success(null);
        }
        return new HttpResult<List<Row>>().success(rowList);
    }

    /**
     * 获取停电信息
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return total
     */
    public HttpResult<OutageInformationVo> getOutageInformation(
            LocalDate beginDate, LocalDate endDate) {
        OutageInformationVo vo = new OutageInformationVo();
        List<Row> rowList = faultDetailDao.selectOutageInformationTotal(beginDate, endDate);
        for (Row row : rowList) {
            String blackoutType = row.getString("BLACKOUT_TYPE");
            switch (blackoutType) {
                case "计划停电":
                    vo.setPlannedOutage(
                            getOutageInformation(beginDate, endDate, row));
                    break;
                case "故障停电":
                    vo.setFaultOutage(
                            getOutageInformation(beginDate, endDate, row));
                    break;
                case "临时停电":
                    vo.setTemporaryOutage(
                            getOutageInformation(beginDate, endDate, row));
                    break;
            }
        }
        return new HttpResult<OutageInformationVo>().success(vo);
    }

    private OutageInformationVo.OutageInformation getOutageInformation(
            LocalDate beginDate, LocalDate endDate, Row row) {
        Row resultRow = faultDetailDao
                .selectOutageInformation(beginDate, endDate, row.getString("BLACKOUT_TYPE"));
        OutageInformationVo.OutageInformation entity =
                new OutageInformationVo.OutageInformation();
        entity.setUsers(resultRow.getLong("USERS", 0L));
        entity.setDistricts(resultRow.getLong("DISTRICTS", 0L));
        entity.setBlackoutTypeTotal(row.getLong("COUNTS", 0L));
        return entity;
    }

    /**
     * 获取重点关注故障线路
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 重点关注故障线路
     */
    public HttpResult<FocusOnFaultyLinesVo> getFocusOnFaultyLines(
            LocalDate beginDate, LocalDate endDate) {
        List<LocalDate> localDateTimes = operationMaintenanceOrderDao
                .selectInspectionCompletionTime(beginDate, endDate);
        if (CollectionUtils.isEmpty(localDateTimes)) {
            return new HttpResult<FocusOnFaultyLinesVo>().success(null);
        }
        List<Row> rowsList = faultDetailDao
                .selectFocusOnFaultyLines(beginDate, endDate);
        long count = 0;
        LocalDate eventTime;
        String eventTimeStr;
        LocalDate closestDate;
        Period period;
        List<FocusOnFaultyLinesVo.FocusOnFaultyLines> focusOnFaultyLinesList = new LinkedList<>();
        for (Row row : rowsList) {
            eventTimeStr = row.getString("EVENT_TIME");
            eventTime = LocalDate.parse(eventTimeStr);
            closestDate = ToolUtils.findClosestDate(localDateTimes, eventTime);
            period = Period.between(eventTime, closestDate);
            if (period.getMonths() < 1 && period.getDays() == 0) {
                count++;
                FocusOnFaultyLinesVo.FocusOnFaultyLines data =
                        new FocusOnFaultyLinesVo.FocusOnFaultyLines();
                data.setLinesName(row.getString("POWER_FAILURE_LINE_NAME"));
                data.setUnitName(row.getString("COUNTY_CORPORATION"));
                data.setInspectionTime(closestDate);
                data.setFaultyCreateTime(eventTime);
                focusOnFaultyLinesList.add(data);
            }
        }
        FocusOnFaultyLinesVo vo = new FocusOnFaultyLinesVo();
        vo.setTotal(count);
        vo.setFocusOnFaultyLinesList(focusOnFaultyLinesList);
        return new HttpResult<FocusOnFaultyLinesVo>().success(vo);
    }

}
