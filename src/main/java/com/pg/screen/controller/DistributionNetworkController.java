package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.model.vo.*;
import com.pg.screen.service.DistributionNetworkService;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配网规模接口
 *
 * @author pengzhi.wang
 */
@RestController
@RequestMapping("/distribution_network/")
@CrossOrigin(origins = "*")
public class DistributionNetworkController {

    @Resource
    private DistributionNetworkService distributionNetworkService;

    /**
     * 查询中压线路
     *
     * @param county 地区
     * @return 中压线路
     */
    @GetMapping("/medium_voltage_line")
    public HttpResult<MediumVoltageLineVO> getMediumVoltageLine(String county) {
        return distributionNetworkService.getMediumVoltageLine(county);
    }

    /**
     * 查询变压器数据
     *
     * @param county 地区
     * @return 变压器数据
     */
    @GetMapping("/transformer")
    public HttpResult<MediumVoltageAccountVO> getTransformer(String county) {
        return distributionNetworkService.getTransformer(county);
    }

    /**
     * 查询开关站-环网柜
     *
     * @param county 地区
     * @return 开关站-环网柜数据
     */
    @GetMapping("/switching_cabinet")
    public HttpResult<SwitchingCabinetVO> getSwitchingCabinetData(String county) {
        return distributionNetworkService.getSwitchingCabinetData(county);
    }

    /**
     * 查询线路设备数据
     *
     * @param county 地区
     * @return 线路设备数据
     */
    @GetMapping("/line_device")
    public HttpResult<LineDeviceVO> getFaultIndicator(String county) {
        return distributionNetworkService.getFaultIndicator(county);
    }

    /**
     * 查询线路设备断路器数据
     *
     * @param county 地区
     * @return 线路设备断路器数据
     */
    @GetMapping("/circuit_breaker")
    public HttpResult<CircuitBreakerVO> getCircuitBreaker(String county) {
        return distributionNetworkService.getCircuitBreaker(county);
    }

    /**
     * 查询线路设备断路器开关数据
     *
     * @param county 地区
     * @return 线路设备断路器开关数据
     */
    @GetMapping("/circuit_breaker_switch")
    public HttpResult<CircuitBreakerSwitchVO> getCircuitBreakerSwitch(String county) {
        return distributionNetworkService.getCircuitBreakerSwitch(county);
    }


    /**
     * 查询开关站内数据
     *
     * @param county 地区
     * @return 开关站内数据
     */
    @GetMapping("/switch_station")
    public HttpResult<SwitchStationVO> getSwitchStation(String county) {
        return distributionNetworkService.getSwitchStation(county);
    }
}
