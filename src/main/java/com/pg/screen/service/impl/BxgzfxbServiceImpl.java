package com.pg.screen.service.impl;

import com.pg.screen.mapper.BxgzfxbMapper;
import com.pg.screen.mapper.dto.BxgzfxbParam;
import com.pg.screen.mapper.dto.BxgzfxbPrintTotalDto;
import com.pg.screen.service.IBxgzfxbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 保修故障维修分析表 服务实现类
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@Service
@Slf4j
public class BxgzfxbServiceImpl  implements IBxgzfxbService {

    @Autowired
    private BxgzfxbMapper bxgzfxbMapper;


    /**
     * 导出统计
     * @return
     */
    @Override
    public String printTotal(BxgzfxbParam queryParams, HttpServletResponse response, HttpServletRequest request) {
        // 根据订单号/发货详情对象， 拼出数据
        List<BxgzfxbPrintTotalDto> vo = bxgzfxbMapper.getPrintTotal(null,queryParams);
        BigDecimal mswhbxl=new BigDecimal(0);
        for(BxgzfxbPrintTotalDto item:vo){
            if(item.getMswhbxl()!=null){
                mswhbxl=mswhbxl.add(BigDecimal.valueOf(item.getMswhbxl()));
            }

        }
        return mswhbxl.toString();

    }


}
