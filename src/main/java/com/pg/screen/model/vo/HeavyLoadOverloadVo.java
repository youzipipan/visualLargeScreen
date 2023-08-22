package com.pg.screen.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配变重过载Vo
 * @author c.chuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeavyLoadOverloadVo extends Xy2DataCountVo {

    /**
     * 配变重载数量
     */
    private Long heavyLoadCount;

    /**
     * 配变过载数量
     */
    private Long overloadCount;

}
