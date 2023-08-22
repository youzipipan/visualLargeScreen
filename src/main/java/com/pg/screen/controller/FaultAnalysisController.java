package com.pg.screen.controller;

import com.mybatisflex.core.row.Row;
import com.pg.screen.common.HttpResult;
import com.pg.screen.enums.WorkOrderUnitEnum;
import com.pg.screen.model.vo.RepairWorkOrderTypeAnalysisVo;
import com.pg.screen.model.vo.RushRepairAvgForUnitVo;
import com.pg.screen.model.vo.XyDataCountVo;
import com.pg.screen.service.RepairsOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 配网故障分析接口
 *
 * @author c.chuang
 */
@RestController
@RequestMapping("fault_analysis")
@CrossOrigin(origins = "*")
public class FaultAnalysisController {

    @Resource
    private RepairsOrderService repairsOrderService;

    /**
     * 获取报修工单类型分析一级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return list
     */
    @GetMapping("work_order_type_1")
    public HttpResult<RepairWorkOrderTypeAnalysisVo> getRepairWorkOrderTypeAnalysis1(
            LocalDate beginDate, LocalDate endDate) {
        if (null == beginDate) {
            beginDate = LocalDate.of(2000, 1, 1);
        }
        if (null == endDate) {
            endDate = LocalDate.now();
        }
        return repairsOrderService.getRepairWorkOrderTypeAnalysis1(beginDate, endDate);
    }

    /**
     * 获取报修工单类型分析二级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    @GetMapping("work_order_type_2")
    public HttpResult<List<Row>> getRwotaSubclass2(
            LocalDate beginDate, LocalDate endDate, String type) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.getRwotaSubclass2(beginDate, endDate, type);
    }

    /**
     * 获取报修工单类型分析三级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    @GetMapping("work_order_type_3")
    public HttpResult<List<Row>> getRwotaSubclass3(
            LocalDate beginDate, LocalDate endDate, String type) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.getRwotaSubclass3(beginDate, endDate, type);
    }

    /**
     * 获取抢修时长分析平均数
     * 返回值说明 key:faultRepairAvg、arriveAtSceneAvg
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    @GetMapping("rush_repair_avg")
    public HttpResult<Map<String, BigDecimal>> getRushRepairAvg(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.now().plusMonths(-1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.getRushRepairAvg(beginDate, endDate);
    }


    /**
     * 获取单位抢修时长分析
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    @GetMapping("rush_repair_avg_unit")
    public HttpResult<RushRepairAvgForUnitVo> getRushRepairAvgForUnit(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.now().plusMonths(-1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.getRushRepairAvgForUnit(beginDate, endDate);
    }

    /**
     * 获取报修工单数量分析-月
     *
     * @return 报修工单数量分析-月
     */
    @GetMapping("quantity_month")
    public HttpResult<Map<Integer, List<Long>>> getRepairQuantityMonth() {
        return repairsOrderService.getRepairQuantityMonth();
    }


    /**
     * 获取报修工单数量分析-小时
     *
     * @param date 日期
     * @return 报修工单数量分析-小时
     */
    @GetMapping("quantity_hour")
    public HttpResult<List<Long>> getRepairQuantityHour(String date) {
        return repairsOrderService.getRepairQuantityHour(date);
    }

    /**
     * 获取配网抢修工单分布
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 配网抢修工单分布
     */
    @GetMapping("work_order_distribution")
    public HttpResult<XyDataCountVo> getWorkOrderDistribution(
            LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.getWorkOrderDistribution(beginDate, endDate);
    }

    /**
     * 获取配网抢修工单分布-抢修班组
     * 接口说明 key:QXBZ(抢修班组) key:COUNTS(数量)
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param unit      工单单位
     * @return 配网抢修工单分布
     */
    @GetMapping("work_order_distribution_unit")
    public HttpResult<List<Row>> getWorkOrderDistributionByUnit(
            LocalDate beginDate, LocalDate endDate, String unit) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        if (StringUtils.isNotBlank(unit)) {
            unit = WorkOrderUnitEnum.getFullName(unit);
        }
        return repairsOrderService
                .getWorkOrderDistributionByUnit(beginDate, endDate, unit);
    }

    /**
     * 获取配网抢修工单分布-抢修人员
     * 接口说明 key:QXRY(抢修人员) key:COUNTS(数量)
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param team      抢修班组
     * @return 配网抢修工单分布
     */
    @GetMapping("work_order_distribution_user")
    public HttpResult<XyDataCountVo> getWorkOrderDistributionByUser(
            LocalDate beginDate, LocalDate endDate, String team, String company) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        if (StringUtils.isEmpty(company)) {
            throw new RuntimeException("抢修工单：分公司参数错误");
        }
        final String companyName = WorkOrderUnitEnum.getName(company);
        return repairsOrderService
                .getWorkOrderDistributionByUser(beginDate, endDate, team, companyName);
    }

    /**
     * 获取十万户报修率
     *
     * @return 十万户报修率
     */
    @GetMapping("10w_repair_rate")
    public HttpResult<BigDecimal> get10wRepairRate(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            beginDate = LocalDate.of(2000, 1, 1);
            endDate = LocalDate.now();
        }
        return repairsOrderService.get10wRepairRate(beginDate, endDate);
    }

}
