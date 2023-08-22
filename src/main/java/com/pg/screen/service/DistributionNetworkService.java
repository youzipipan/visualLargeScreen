package com.pg.screen.service;

import com.pg.screen.common.HttpResult;
import com.pg.screen.dao.*;
import com.pg.screen.model.vo.*;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author pengzhi.wang
 */
@Service
@Slf4j
public class DistributionNetworkService {

    @Resource
    private MediumVoltageLineDao mediumVoltageLineDao;

    @Resource
    private MediumVoltageAccountDao mediumVoltageAccountDao;

    @Resource
    private SwitchingCabinetDao switchingCabinetDao;

    @Resource
    private FaultIndicatorDao faultIndicatorDao;

    @Resource
    private CircuitBreakerDao circuitBreakerDao;

    @Resource
    private SwitchStationDao switchStationDao;

    /**
     * 根据地区查询馈线数据
     *
     * @param county 地区
     * @return 中压线路馈线数据
     */
    public HttpResult<MediumVoltageLineVO> getMediumVoltageLine(String county) {
        Long aLong1 = mediumVoltageLineDao.selectOverheadLineCount(county);
        Long aLong2 = mediumVoltageLineDao.selectCableLineCount(county);
        Long aLong3 = mediumVoltageLineDao.selectHybridLineCount(county);
        return new HttpResult<MediumVoltageLineVO>().success(MediumVoltageLineVO.builder()
                .overheadLineCount(aLong1)
                .overheadLineTotal(mediumVoltageLineDao.selectOverheadLineTotal(county))
                .cableLineCount(aLong2)
                .cableLineTotal(mediumVoltageLineDao.selectCableLineTotal(county))
                .hybridLineCount(aLong3)
                .hybridLineTotal(mediumVoltageLineDao.selectHybridLineTotal(county))
                .feederCount(aLong1 + aLong2 + aLong3)
                .build());
    }

    /**
     * 根据地区查询配网变压器数量
     *
     * @param county 地区
     * @return 配网变压器数量
     */
    public HttpResult<MediumVoltageAccountVO> getTransformer(String county) {
        BigDecimal bigDecimal = mediumVoltageAccountDao.selectPublicTransformerCount(county);
        BigDecimal bigDecimal1 = mediumVoltageAccountDao.selectSpecialTransformerCount(county);
        return new HttpResult<MediumVoltageAccountVO>().success(MediumVoltageAccountVO.builder()
                .publicTransformerCount(bigDecimal)
                .publicTransformerCapacity(mediumVoltageAccountDao.selectPublicTransformerCapacity(county))
                .specialTransformerCount(bigDecimal1)
                .specialTransformerCapacity(mediumVoltageAccountDao.selectSpecialTransformerCapacity(county))
                .transformersCount(bigDecimal.add(bigDecimal1))
                .build());
    }


    /**
     * 根据地区查询开关站-环网贵数据
     *
     * @param county 地区
     * @return 开关站-环网贵数据
     */
    public HttpResult<SwitchingCabinetVO> getSwitchingCabinetData(String county) {
        return new HttpResult<SwitchingCabinetVO>().success(SwitchingCabinetVO.builder()
                .switchCount(switchingCabinetDao.selectSwitchingCount(county))
                .cabinetCount(switchingCabinetDao.selectCabinetCount(county))
                .build());
    }

    /**
     * 根据地区查询故障指示器数据
     *
     * @param county 地区
     * @return 故障指示器数据
     */
    public HttpResult<LineDeviceVO> getFaultIndicator(String county) {
        return new HttpResult<LineDeviceVO>().success(LineDeviceVO.builder()
                .count(faultIndicatorDao.selectFaultIndicatorCount(county))
                .type(faultIndicatorDao.selectTypeCount(county))
                .build());
    }

    /**
     * 根据地区查询断路器总数量
     *
     * @param county 地区
     * @return 线路设备断路器数据
     */
    public HttpResult<CircuitBreakerVO> getCircuitBreaker(String county) {
        return new HttpResult<CircuitBreakerVO>().success(CircuitBreakerVO.builder()
                .circuitBreakerCount(circuitBreakerDao.selectCircuitBreakerCount(county))
                .threeRemoteCount(circuitBreakerDao.selectThreeRemoteCount(county))
                .normalCount(circuitBreakerDao.selectNormalCount(county))
                .build());
    }


    /**
     * 根据地区查询断路器总数量
     *
     * @param county 地区
     * @return 线路设备断路器数据
     */
    public HttpResult<CircuitBreakerSwitchVO> getCircuitBreakerSwitch(String county) {
        return new HttpResult<CircuitBreakerSwitchVO>().success(CircuitBreakerSwitchVO.builder()
                .circuitBreakerSwitchCount(circuitBreakerDao.selectCircuitBreakerSwitchCount(county))
                .threeRemoteSwitchCount(circuitBreakerDao.selectThreeRemoteSwitchCount(county))
                .normalSwitchCount(circuitBreakerDao.selectNormalSwitchCount(county))
                .build());
    }

    /**
     * 根据地区查询开关站内数据
     *
     * @param county 地区
     * @return 开关站内数据
     */
    public HttpResult<SwitchStationVO> getSwitchStation(String county) {
        return new HttpResult<SwitchStationVO>().success(SwitchStationVO.builder()
                .switchStationCount(switchStationDao.selectCountByCounty(county))
                .threeRemoteCount(switchStationDao.selectThreeRemoteCount(county))
                .normalCount(switchStationDao.selectNormalCount(county))
                .build());
    }

}
