package com.pg.screen.controller;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.BusinessException;
import com.pg.screen.common.HttpResult;
import com.pg.screen.model.vo.*;
import com.pg.screen.service.ReliabilityAnalysisService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * 可靠性分析接口
 *
 * @author c.chuang
 */
@RestController
@RequestMapping("/ra/")
@CrossOrigin(origins = "*")
public class ReliabilityAnalysisController {

    @Resource
    private ReliabilityAnalysisService reliabilityAnalysisService;

    /**
     * 停电时户数消耗
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("power_cut_hour_unit_consume")
    public HttpResult<List<Row>> getPowerCutHourUnitConsume(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            endDate = LocalDate.now();
        }
        return reliabilityAnalysisService.getPowerCutHourUnitConsume(beginDate, endDate);
    }

    /**
     * 获取大范围停电明细
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("big_detail")
    public HttpResult<List<Row>> getExtensivePowerOutageDetails(LocalDate beginDate, LocalDate endDate) {
        return reliabilityAnalysisService.getExtensivePowerOutageDetails(beginDate, endDate);
    }

    /**
     * 获取大范围停电情况
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("big_situation")
    public HttpResult<ExtensivePowerOutageSituationVo> getExtensivePowerOutageSituation(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            endDate = LocalDate.now();
        }
        return reliabilityAnalysisService.getExtensivePowerOutageSituation(beginDate, endDate);
    }

    /**
     * 超长停电情况
     *
     * @return x是返回值
     */
    @GetMapping("prolonged_power_failure")
    public HttpResult<ProlongedPowerFailureVo> getProlongedPowerFailure(LocalDate endDate) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return reliabilityAnalysisService.getProlongedPowerFailure(endDate);
    }

    /**
     * 获取重复停电情况
     *
     * @param endDate 结束日期
     * @return list
     */
    @GetMapping("repeated_outage_condition")
    public HttpResult<XyDataCountCountVo> getRepeatedOutageCondition(LocalDate endDate) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return reliabilityAnalysisService.getRepeatedOutageCondition(endDate);
    }

    /**
     * 获取用户平均停电时间
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    @GetMapping("mean_outage_time")
    public HttpResult<MeanOutageTimeVo> getMeanOutageTime(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            endDate = LocalDate.now();
        }
        return reliabilityAnalysisService.getMeanOutageTime(beginDate, endDate);
    }

    /**
     * 获取供电可靠性管理
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0全口径 1城网 2农网
     * @return vo
     */
    @GetMapping("reliability_management")
    public HttpResult<ReliabilityManagementVo> getReliabilityManagement(
            LocalDate beginDate, LocalDate endDate, Integer type) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            endDate = LocalDate.now();
        }
        if (type == null) {
            throw new BusinessException("参数type不可以为空");
        }
        return reliabilityAnalysisService.getReliabilityManagement(beginDate, endDate, type);
    }

}
