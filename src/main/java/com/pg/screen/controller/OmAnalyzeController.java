package com.pg.screen.controller;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.entity.DefectLibraryCountVo;
import com.pg.screen.mapper.entity.DefectLibraryInfoVo;
import com.pg.screen.model.vo.*;
import com.pg.screen.service.OmAnalyzeService;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 运维分析接口
 *
 * @author c.chuang
 */
@RestController
@RequestMapping("/om/")
@CrossOrigin(origins = "*")
public class OmAnalyzeController {

    @Resource
    private OmAnalyzeService omAnalyzeService;

    /**
     * 获取配变重过载-柱状图
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 配变重过载
     */
    @GetMapping("heavy_load_over_load_bar")
    public HttpResult<HeavyLoadOverloadVo> getHeavyLoadOverload(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getHeavyLoadOverload(beginDate, endDate);
    }

    /**
     * 获取配变低电压-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("low_voltage_bar")
    public HttpResult<XyDataCountCountVo> getFaultTypeForLowVoltage(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getUnitByFaultType(
                "配变低电压", beginDate, endDate);
    }

    /**
     * 获取三相不平衡-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("unbalance_bar")
    public HttpResult<XyDataCountCountVo> getFaultTypeForUnbalance(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getUnitByFaultType(
                "三相电流不平衡", beginDate, endDate);
    }

    /**
     * 获取异常台区治理情况-三相不平衡-饼图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 处理结果
     */
    @GetMapping("abnormal_unbalance_pie")
    public HttpResult<ExceptionHandlingPieVo> getProcessingResultByTypeForUnbalance(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getProcessingResultByType(
                "三相电流不平衡", beginDate, endDate);
    }

    /**
     * 获取异常台区治理情况-重过载-饼图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 处理结果
     */
    @GetMapping("abnormal_ho_pie")
    public HttpResult<ExceptionHandlingPieVo> getProcessingResultByType2(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getProcessingResultByType(
                "重过载", beginDate, endDate);
    }

    /**
     * 获取异常台区治理情况-低电压-饼图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 处理结果
     */
    @GetMapping("abnormal_low_voltage_pie")
    public HttpResult<ExceptionHandlingPieVo> getProcessingResultByTypeForLowVoltage(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getProcessingResultByType(
                "配变低电压", beginDate, endDate);
    }

    /**
     * 获取异常台区处理情况-三相不平衡-柱状图
     *
     * @param type      1:未处理 2:已处理 默认null/0
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 异常台区处理情况-柱状图
     */
    @GetMapping("abnormal_unbalance_bar")
    public HttpResult<XyDataCountVo> getExceptionHandlingForUnbalance(
            Integer type, LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getExceptionHandlingForBar(
                type, "三相电流不平衡", beginDate, endDate);
    }

    /**
     * 获取异常台区处理情况-重过载-柱状图
     *
     * @param type      1:未处理 2:已处理 默认null
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 异常台区处理情况-柱状图
     */
    @GetMapping("abnormal_ho_bar")
    public HttpResult<XyDataCountVo> getExceptionHandlingForHo(
            Integer type, LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getExceptionHandlingForBar(
                type, "重过载", beginDate, endDate);
    }

    /**
     * 获取异常台区处理情况-低电压-柱状图
     *
     * @param type      1:未处理 2:已处理 默认null
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 异常台区处理情况-柱状图
     */
    @GetMapping("abnormal_low_voltage_bar")
    public HttpResult<XyDataCountVo> getExceptionHandlingForLowVoltage(
            Integer type, LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return omAnalyzeService.getExceptionHandlingForBar(
                type, "配变低电压", beginDate, endDate);
    }

    /**
     * 获取公变经济运行情况-数量和百分比
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 公变经济运行情况-数量和百分比
     */
    @GetMapping("economic_operation_count")
    public HttpResult<EconomicOperationVo> getEconomicOperation(LocalDate beginDate, LocalDate endDate) {
        return omAnalyzeService.getEconomicOperation(beginDate, endDate);
    }

    /**
     * 获取公变经济运行情况-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 柱状图数据
     */
    @GetMapping("economic_operation_unit")
    public HttpResult<XyDataCountVo> getEconomicOperationUnitsCounts(LocalDate beginDate, LocalDate endDate) {
        return omAnalyzeService.getEconomicOperationUnitsCounts(beginDate, endDate);
    }

    /**
     * 获取公变经济运行情况-柱状图-根据点击百分比显示柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type 1:0-20 2:20-50 3:50-70 4:70-100 5:100以上
     * @return 柱状图数据
     */
    @GetMapping("economic_operation_unit_click")
    public HttpResult<XyDataCountVo> getEconomicOperationUnitsCountsClick(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        if (type == null) {
            type = 1;
        }
        return omAnalyzeService.getEconomicOperationUnitsCounts(beginDate, endDate, type);
    }

    /**
     * 获取线路巡视统计数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 线路巡视统计数量
     */
    @GetMapping("line_patrol_statistics_count")
    public HttpResult<LinePatrolStatisticsCountVo> getLinePatrolStatisticsCount(
            LocalDate beginDate, LocalDate endDate) {
        return omAnalyzeService.getLinePatrolStatisticsCount(beginDate, endDate);
    }

    /**
     * 获取线路巡视统计数量-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0/null全部 1未巡视 2已巡视
     * @return 线路巡视统计数量
     */
    @GetMapping("line_patrol_statistics_bar")
    public HttpResult<XyDataCountVo> getLinePatrolStatisticsByUnit(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        if (type == null) {
            type = 0;
        }
        return omAnalyzeService.getLinePatrolStatisticsByUnit(beginDate, endDate, type);
    }

    /**
     * 获取线路巡视统计-饼图
     * 返回值说明：TASK_STATUS=已处理/未处理 COUNTS=数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("line_patrol_statistics_pie")
    public HttpResult<List<Row>> getInspectionTourByStatus(LocalDate beginDate, LocalDate endDate) {
        return omAnalyzeService.getInspectionTourByStatus(beginDate, endDate);
    }

    /**
     * 获取缺陷处理统计数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    @GetMapping("work_order_status_count")
    public HttpResult<DefectLibraryCountVo> getWorkOrderStatusCount(LocalDate beginDate, LocalDate endDate) {
        return omAnalyzeService.getWorkOrderStatusCount(beginDate, endDate);
    }

    /**
     * 获取缺陷处理统计-柱状图
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0/null全部 1已消缺 2未消缺
     * @return list
     */
    @GetMapping("work_order_status_unit")
    public HttpResult<XyDataCountVo> getWorkOrderStatusByUnit(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        if (type == null) {
            type = 0;
        }
        return omAnalyzeService.getWorkOrderStatusByUnit(beginDate, endDate, type);
    }

    /**
     * 缺陷模块数据统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("patrol_flaw_statistics_count")
    public HttpResult<List<DefectLibraryInfoVo>> getWorkOrder(
            String beginDate, String endDate) {
        return omAnalyzeService.getWorkOrderInfo(beginDate, endDate);
    }

}
