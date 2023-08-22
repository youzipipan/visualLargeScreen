package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.entity.FaultRepair;
import com.pg.screen.model.vo.FaultRepairCountVO;
import com.pg.screen.model.vo.FaultRepairFlagCountVO;
import com.pg.screen.model.vo.HeavyLoadOverloadVo;
import com.pg.screen.service.FaultRepairService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 故障——主动抢修 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-07-16
 */
@RestController
@RequestMapping("/fault_repair/")
@CrossOrigin(origins = "*")
public class FaultRepairController {

    @Resource
    private FaultRepairService faultRepairService;

    @GetMapping("fault_repair_count")
    public HttpResult<FaultRepairCountVO> findFaultRepairCount() {
        return faultRepairService.findCount();
    }

    @GetMapping("flag_count")
    public HttpResult<List<FaultRepairFlagCountVO>> findFaultRepairFlagCount(String type) {
        return faultRepairService.findFaultRepairFlagCount(type);
    }

    @GetMapping("list")
    public HttpResult<List<FaultRepair>> selectListData(String flag, String type) {
        return faultRepairService.selectListData(flag, type);
    }
}
