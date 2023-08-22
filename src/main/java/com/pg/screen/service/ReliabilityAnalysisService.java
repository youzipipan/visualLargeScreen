package com.pg.screen.service;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.DpGdkkxZbDao;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.model.vo.*;
import com.pg.screen.utils.ToolUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 可靠性分析业务
 *
 * @author c.chuang
 */
@Service
public class ReliabilityAnalysisService {

    @Resource
    private DpGdkkxZbDao dpGdkkxZbDao;

    public HttpResult<List<Row>> getPowerCutHourUnitConsume(LocalDate beginDate, LocalDate endDate) {
        List<Row> rowsList = new LinkedList<>();
        List<Object> annualTargetList = Arrays.asList(
                1.74, 1798, 1952, 10543, 14655, 64716, 23746, 72901, 79877, 88014, 2133, 17043, 15776, 11582);
        List<String> unitList = new ArrayList<>();
        unitList.add("大连");
        unitList.addAll(WorkOrderUnitEnum.getShortName());
        for (int i = 0; i < annualTargetList.size(); i++) {
            Row row = new Row();
            row.put("unit", unitList.get(i));
            row.put("annualTarget", annualTargetList.get(i));
            rowsList.add(row);
        }
        List<Row> tdzshsList = dpGdkkxZbDao.selectTdzshs(beginDate, endDate);
        String unitStr;
        String ssxq;
        boolean isFlag = false;
        Date rowDate;
        Date tdzshsDate;
        double consumeValue = 0.0;
        boolean isOneMonthDay = beginDate.isEqual(
                LocalDate.of(LocalDate.now().getYear(), 1, 1));
        for (Row row : rowsList) {
            unitStr = row.getString("unit");
            for (Row tdzshsRow : tdzshsList) {
                ssxq = tdzshsRow.getString("SSXQ");
                if (unitStr.equals(ssxq)) {
                    rowDate = row.getDate("date");
                    tdzshsDate = tdzshsRow.getDate("ZZSJ");
                    if (rowDate == null ||
                            (isOneMonthDay && tdzshsDate.compareTo(rowDate) >= 0)) {
                        row.set("consume", tdzshsRow.getDouble("TDZSHS"));
                        row.set("date", tdzshsRow.getDate("ZZSJ"));
                    } else if (!isOneMonthDay) {
                        consumeValue = consumeValue + tdzshsRow.getDouble("TDZSHS");
                        row.set("consume", consumeValue);
                        row.set("date", tdzshsRow.getDate("ZZSJ"));
                    }
                    isFlag = true;
                }
            }
            if (!isFlag) {
                row.put("consume", 0);
            }
            isFlag = false;
        }
        removeRow(rowsList);
        LocalDate yearBasisDate = beginDate.plusYears(-1);
        LocalDate yearBasisEndDate = endDate.plusYears(-1);
        List<Row> yearBasisList = dpGdkkxZbDao.selectTdzshs(yearBasisDate, yearBasisEndDate);
        double consumeNum;
        double yearConsumeNum;
        double computeNum;
        for (Row row : rowsList) {
            unitStr = row.getString("unit");
            consumeNum = row.getDouble("consume");
            for (Row yearRow : yearBasisList) {
                ssxq = yearRow.getString("SSXQ");
                if (unitStr.equals(ssxq)) {
                    rowDate = row.getDate("date");
                    tdzshsDate = yearRow.getDate("ZZSJ");
                    if (rowDate == null ||
                            (isOneMonthDay && tdzshsDate.compareTo(rowDate) >= 0)) {
                        yearConsumeNum = yearRow.getDouble("TDZSHS");
                        row.set("date", yearRow.getDate("ZZSJ"));
                        computeNum = consumeNum - yearConsumeNum;
                        row.set("onAYearOnYearBasis", BigDecimal.valueOf(computeNum)
                                .setScale(2, RoundingMode.HALF_UP));
                    } else if (!isOneMonthDay) {
                        consumeValue = consumeValue + yearRow.getDouble("TDZSHS");
                        computeNum = consumeNum - consumeValue;
                        row.set("onAYearOnYearBasis", BigDecimal.valueOf(computeNum)
                                .setScale(2, RoundingMode.HALF_UP));
                    }
                    isFlag = true;
                }
            }
            if (!isFlag) {
                row.put("onAYearOnYearBasis", 0);
            }
        }
        removeRow(rowsList);
        //List<Row> currentPeriodList = dpGdkkxZbDao.selectTdzshs(currentPeriodDate, currentPeriodEndDate);
        for (Row row : rowsList) {
            unitStr = row.getString("unit");
            for (Row currentPeriodRow : tdzshsList) {
                ssxq = currentPeriodRow.getString("SSXQ");
                if (unitStr.equals(ssxq)) {
                    rowDate = row.getDate("date");
                    tdzshsDate = currentPeriodRow.getDate("QSSJ");
                    if (rowDate == null ||
                            tdzshsDate.compareTo(rowDate) >= 0) {
                        row.set("date", currentPeriodRow.getDate("QSSJ"));
                        row.set("currentConsumption", currentPeriodRow.getDouble("TDZSHS"));
                    }
                }
            }
            if (!isFlag) {
                row.put("currentConsumption", 0);
            }
        }
        removeRow(rowsList);
        return new HttpResult<List<Row>>().success(rowsList);
    }

    private void removeRow(List<Row> list) {
        for (Row row : list) {
            row.remove("date");
        }
    }

    /**
     * 获取大范围停电明细
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<List<Row>> getExtensivePowerOutageDetails(LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = dpGdkkxZbDao.selectWidespreadPowerOutage(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<List<Row>>().success(null);
        }
        return new HttpResult<List<Row>>().success(rows);
    }

    /**
     * 获取大范围停电情况
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<ExtensivePowerOutageSituationVo> getExtensivePowerOutageSituation(
            LocalDate beginDate, LocalDate endDate) {
        Row annualAccumulationRow = dpGdkkxZbDao.selectAnnualCumulativeOutage(beginDate, endDate, false);
        if (annualAccumulationRow == null) {
            return new HttpResult<ExtensivePowerOutageSituationVo>().success(null);
        }
        BigDecimal annualAccumulation = annualAccumulationRow.getBigDecimal("DFWTDSHS");
        Row happenThisWeekRow = dpGdkkxZbDao.selectAnnualCumulativeOutage(beginDate, endDate, true);
        BigDecimal happenThisWeek = BigDecimal.ZERO;
        if (happenThisWeekRow != null) {
            happenThisWeek = happenThisWeekRow.getBigDecimal("DFWTDSHS");
        }
        int happenThisWeekPercent = 0;
        if (annualAccumulation.compareTo(BigDecimal.ZERO) != 0) {
            happenThisWeekPercent = happenThisWeek
                    .divide(annualAccumulation, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).intValue();
        }
        List<Row> annualAccumulationBarRow = dpGdkkxZbDao
                .selectAnnualCumulativeOutageForUnit(beginDate, endDate, false);
        for (Row row : annualAccumulationBarRow) {
            String unitName = WorkOrderUnitEnum.getFullName(row.getString("DWMC"));
            row.set("DWMC", unitName);
        }
        List<Double> annualAccumulationBarList = ToolUtils.buildListForDouble(
                annualAccumulationBarRow, "DWMC", "DFWTDSHS");
        List<Row> happenThisWeekRowBarRow = dpGdkkxZbDao.selectAnnualCumulativeOutageForUnit(
                beginDate, endDate, true);
        List<Double> happenThisWeekRowBarList = ToolUtils.buildListForDouble(
                happenThisWeekRowBarRow, "DWMC", "DFWTDSHS");
        ExtensivePowerOutageSituationVo vo = new ExtensivePowerOutageSituationVo();
        vo.setAnnualAccumulation(annualAccumulation);
        vo.setHappenThisWeek(happenThisWeek);
        vo.setHappenThisWeekPercent(happenThisWeekPercent);
        vo.setXAxisNameList(WorkOrderUnitEnum.getShortName());
        vo.setYAxisNameList(annualAccumulationBarList);
        vo.setY2AxisNameList(happenThisWeekRowBarList);
        return new HttpResult<ExtensivePowerOutageSituationVo>().success(vo);
    }

    /**
     * 获取超长停电情况
     *
     * @param endDate 结束日期
     * @return total
     */
    public HttpResult<ProlongedPowerFailureVo> getProlongedPowerFailure(LocalDate endDate) {
        ProlongedPowerFailureVo vo = new ProlongedPowerFailureVo();
        int month = LocalDate.now().getMonth().getValue();
        BigDecimal hour = BigDecimal.valueOf(month).multiply(BigDecimal.valueOf(8));
        vo.setHour(hour);
        Long total = dpGdkkxZbDao.selectTableTotal(endDate, "DP_CCTD", "CCTDYHS");
        vo.setTotal(total);
        List<Row> rows = dpGdkkxZbDao.selectTdcsListForUnit(endDate);
        List<Long> list = ToolUtils.buildList(rows, "DWMC", "CCTDYHS");
        vo.setXAxisNameList(WorkOrderUnitEnum.getShortName());
        vo.setYAxisNameList(list);
        return new HttpResult<ProlongedPowerFailureVo>().success(vo);
    }

    /**
     * 获取重复停电情况
     *
     * @param endDate 结束日期
     * @return list
     */
    public HttpResult<XyDataCountCountVo> getRepeatedOutageCondition(LocalDate endDate) {
        Long total = dpGdkkxZbDao.selectTableTotal(endDate, "DP_CFTDCS", "BQCTYHS");
        List<Row> rows = dpGdkkxZbDao.selectBqctyhsListForUnit(endDate);
        List<Long> list = ToolUtils.buildList(rows, "DWMC", "BQCTYHS");
        XyDataCountCountVo vo = new XyDataCountCountVo(
                WorkOrderUnitEnum.getShortName(), list, total);
        return new HttpResult<XyDataCountCountVo>().success(vo);
    }

    /**
     * 获取用户平均停电时间
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public HttpResult<MeanOutageTimeVo> getMeanOutageTime(LocalDate beginDate, LocalDate endDate) {
        List<Row> rows = dpGdkkxZbDao.selectMeanOutageTime(beginDate, endDate);
        if (CollectionUtils.isEmpty(rows)) {
            return new HttpResult<MeanOutageTimeVo>().success(null);
        }
        LocalDate beginDatePlus = beginDate.plusYears(-1);
        LocalDate endDatePlus = beginDate.plusYears(-1);
        BigDecimal fullAperture = BigDecimal.ZERO;
        BigDecimal prearrangedOutage = BigDecimal.ZERO;
        BigDecimal faultOutage = BigDecimal.ZERO;
        double yearOnYearBasis;
        for (Row row : rows) {
            fullAperture = fullAperture.add(new BigDecimal(row.getString("PJTDSJ")));
            prearrangedOutage = prearrangedOutage.add(new BigDecimal(row.getString("PJYTDSJ")));
            faultOutage = faultOutage.add(new BigDecimal(row.getString("PJGZTDSJ")));
        }
        double oldYearOnYearBasis = 0;
        List<Row> yearOnYearBasisList = dpGdkkxZbDao.selectMeanOutageTime(beginDatePlus, endDatePlus);
        if (!CollectionUtils.isEmpty(yearOnYearBasisList)) {
            oldYearOnYearBasis = yearOnYearBasisList.stream()
                    .mapToDouble(v -> v.getDouble("PJYTDSJ")).sum();
        }
        yearOnYearBasis = prearrangedOutage.doubleValue() - oldYearOnYearBasis;
        MeanOutageTimeVo vo = new MeanOutageTimeVo();
        vo.setFullAperture(fullAperture.doubleValue());
        vo.setPrearrangedOutage(prearrangedOutage.doubleValue());
        vo.setFaultOutage(faultOutage.doubleValue());
        vo.setYearOnYearBasis(yearOnYearBasis);
        List<Double> pjytdsjList = getBuildList(beginDate, endDate, "PJYTDSJ");
        vo.setYAxisNameList(pjytdsjList);
        List<Double> pjgztdsjList = getBuildList(beginDate, endDate, "PJGZTDSJ");
        vo.setY2AxisNameList(pjgztdsjList);
        vo.setXAxisNameList(WorkOrderUnitEnum.getShortName());
        return new HttpResult<MeanOutageTimeVo>().success(vo);
    }

    private List<Double> getBuildList(LocalDate beginDate, LocalDate endDate, String columns) {
        List<Row> unitList;
        if (beginDate.isEqual(LocalDate.of(
                LocalDate.now().getYear(), 1, 1))) {
            unitList = dpGdkkxZbDao.selectMeanOutageTimeForUnit(
                    beginDate, endDate, true, columns);
        } else {
            unitList = dpGdkkxZbDao.selectMeanOutageTimeForUnit(
                    beginDate, endDate, false, columns);
            Map<String, List<Row>> groupMap = unitList.stream().collect(Collectors.groupingBy(
                    v -> v.getString("SSXQ")));
            List<Row> resultRow = new ArrayList<>();
            double value;
            for (Map.Entry<String, List<Row>> entry : groupMap.entrySet()) {
                Row row = new Row();
                row.put("SSXQ", entry.getKey());
                value = entry.getValue().stream()
                        .mapToDouble(v -> v.getDouble(columns)).sum();
                row.put(columns, value);
                resultRow.add(row);
            }
            unitList.addAll(resultRow);
        }
        String ssxq;
        for (Row row : unitList) {
            ssxq = WorkOrderUnitEnum.getFullName(row.getString("SSXQ"));
            row.set("SSXQ", ssxq);
        }
        return ToolUtils.buildListForDouble(
                unitList, "SSXQ", columns);
    }

    /**
     * 获取供电可靠性管理
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0全口径 1城网 2农网
     * @return vo
     */
    public HttpResult<ReliabilityManagementVo> getReliabilityManagement(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        ReliabilityManagementVo vo = new ReliabilityManagementVo();
        Row rowFullAperture = dpGdkkxZbDao.selectReliability(beginDate, endDate, type);
        BigDecimal reliable = BigDecimal.ZERO;
        BigDecimal blackoutTime = BigDecimal.ZERO;
        BigDecimal hourFamilyNum = BigDecimal.ZERO;
        if (rowFullAperture != null) {
            reliable = rowFullAperture.getBigDecimal("PJGDSJ")
                    .setScale(2, RoundingMode.HALF_UP);
            vo.setReliability(reliable);
            blackoutTime = rowFullAperture.getBigDecimal("PJTDSJ")
                    .setScale(2, RoundingMode.HALF_UP);
            vo.setBlackoutTime(blackoutTime);
            hourFamilyNum = rowFullAperture.getBigDecimal("TDZSHS")
                    .setScale(2, RoundingMode.HALF_UP);
            vo.setHourFamilyNum(hourFamilyNum);
        }
        BigDecimal reliableYear = BigDecimal.ZERO;
        BigDecimal blackoutTimeYear = BigDecimal.ZERO;
        BigDecimal hourFamilyNumYear = BigDecimal.ZERO;
        LocalDate beginDatePlus = beginDate.plusYears(-1);
        LocalDate endDatePlus = endDate.plusYears(-1);
        Row rowFullApertureYear = dpGdkkxZbDao.selectReliability(beginDatePlus, endDatePlus, type);
        if (rowFullApertureYear != null) {
            reliableYear = rowFullApertureYear.getBigDecimal("PJGDSJ");
            blackoutTimeYear = rowFullApertureYear.getBigDecimal("PJTDSJ");
            hourFamilyNumYear = rowFullApertureYear.getBigDecimal("TDZSHS");
        }
        vo.setReliableYear(reliable.subtract(reliableYear));
        vo.setBlackoutTimeYear(blackoutTime.subtract(blackoutTimeYear)
                .setScale(2,RoundingMode.HALF_UP));
        vo.setHourFamilyNumYear(hourFamilyNum.subtract(hourFamilyNumYear));
        return new HttpResult<ReliabilityManagementVo>().success(vo);
    }

}
