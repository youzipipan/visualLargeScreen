package com.pg.screen.controller;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.model.vo.*;
import com.pg.screen.service.FaultDetailService;
import com.pg.screen.utils.ToolUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 配网运行分析接口
 *
 * @author c.chuang
 */
@RestController
@RequestMapping("operation")
@CrossOrigin(origins = "*")
public class OperationAnalysisController {

    @Resource
    private FaultDetailService faultDetailService;

    /**
     * 线路故障分析-获取故障数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障数量
     */
    @GetMapping("fault_total")
    public HttpResult<Long> getTotal(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getTotal(beginDate, endDate);
    }


    /**
     * 线路故障分析-根据故障类型获取故障数量
     *
     * @param faultType 故障类型（重合不良、重合良好、接地故障）
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    @GetMapping("line_fault_analysis_count")
    public HttpResult<XyDataCountVo> getLineFaultAnalysisCount(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFaultAnalysisCount(faultType, beginDate, endDate);
    }

    /**
     * 线路故障分析-根据支线故障获取故障数量
     * 返回值说明:yAxisNameList表示自动跳闸 y2AxisNameList表示主动跳闸
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    @GetMapping("line_fault_analysis_count_branch")
    public HttpResult<Xy2DataCountVo> getLineFaultAnalysisCountByBranch(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService
                .getLineFaultAnalysisCountByBranch(beginDate, endDate);
    }

    /**
     * 线路故障分析-获得故障类型的数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障类型的数量
     */
    @GetMapping("fault_type_count")
    public HttpResult<FaultTypeCountVo> getCountByFaultType(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getCountByFaultType(beginDate,endDate);
    }

    /**
     * 线路故障修复时长-获取线路故障平均修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 线路故障平均修复时长
     */
    @GetMapping("line_fault_repair_avg_total")
    public HttpResult<BigDecimal> getLineFaultRepairDurationAvgTotal(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFaultRepairDurationAvgTotal(beginDate,endDate);
    }

    /**
     * 线路故障修复时长-获取故障修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障修复时长
     */
    @GetMapping("line_fault_repair_avg")
    public HttpResult<XyDataCountVo> getLineFaultRepairDurationAvg(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFaultRepairDurationAvg(beginDate,endDate);
    }

    /**
     * 线路故障修复时长-获取影响用户数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 影响用户数
     */
    @GetMapping("affect_users_number")
    public HttpResult<XyDataCountVo> getAffectUsersNumber(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getAffectUsersNumber(beginDate,endDate);
    }

    /**
     * 线路故障修复时长-获取超8小时故障
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 超8小时故障
     */
    @GetMapping("8_hour_failure")
    public HttpResult<XyDataCountVo> get8HourFailure(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.get8HourFailure(beginDate,endDate);
    }


    /**
     * 获取频繁故障线路数据
     *
     * @param beginMonth 开始月
     * @param endMonth   结束月
     * @param year       年
     * @param type       1：月度累计 2：年度累计
     * @return 频繁故障线路
     */
    @GetMapping("frequent_failure")
    public HttpResult<FrequentFailureVo> getFrequentFailure(
            String beginMonth, String endMonth, String year, Integer type) {
        if (StringUtils.isBlank(beginMonth) || StringUtils.isBlank(endMonth)) {
            String[] initDates = ToolUtils.getBeforeDates(1);
            assert initDates != null;
            beginMonth = initDates[1];
            endMonth = initDates[0];
        }
        if (StringUtils.isBlank(year)) {
            year = String.valueOf(LocalDate.now().getYear());
        }
        if (type == null) {
            type = 2;
        }
        if (StringUtils.equals(beginMonth, endMonth)) {
            throw new RuntimeException("频繁故障线路-参数错误1");
        }
        return faultDetailService.getFrequentFailure(beginMonth, endMonth, year, type);
    }

    /**
     * 获取线路故障率排名
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("line_failure_rate_ranking")
    public HttpResult<XyDataBigDecimalVo> getLineFailureRateRanking(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFailureRateRanking(beginDate, endDate);
    }

    /**
     * 获取线路故障成因分析
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("line_failure_cause_analysis")
    public HttpResult<LineFailureCauseAnalysisVo> getLineFailureCauseAnalysis(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFailureCauseAnalysis(beginDate, endDate);
    }


    /**
     * 获取线路故障成因分析-根据故障原因
     * 返回值Map说明 key:FAULT_CAUSE_2_CATEGORY value:COUNTS
     *
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @param faultCause 故障原因
     * @return list
     */
    @GetMapping("lfca_by_fault_cause")
    public HttpResult<List<Row>> getLineFailureCauseAnalysisByFaultCause(
            LocalDate beginDate, LocalDate endDate, String faultCause) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getLineFailureCauseAnalysisByFaultCause(
                beginDate, endDate, faultCause);
    }

    /**
     * 获取停电信息
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return total
     */
    @GetMapping("outage_information")
    public HttpResult<OutageInformationVo> getOutageInformation(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getOutageInformation(beginDate, endDate);
    }

    /**
     * 获取重点关注故障线路
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 重点关注故障线路
     */
    @GetMapping("focus_on_faulty_lines")
    public HttpResult<FocusOnFaultyLinesVo> getFocusOnFaultyLines(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return faultDetailService.getFocusOnFaultyLines(beginDate, endDate);
    }

}
