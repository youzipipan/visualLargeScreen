package com.pg.screen.mapper.dto;

import lombok.Data;

/**
 * 故障工单95598
 * 
 * @author 
 * @email 
 * @date 2023-03-31 17:28:06
 */
@Data
public class BxgzfxbPrintTotalDto {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	private String rownum;
	/**
	 * 单位
	 */
	private String gddw;
	/**
	 * 高压故障
	 */
	private Integer gygznum;

	/**
	 * 低压故障
	 */
	private Integer dynum;

	/**
	 * 电能质量
	 */
	private Integer dnzlnum;

	/**
	 * 非电力故障
	 */
	private Integer fdlnum;

	/**
	 * 计量故障
	 */
	private Integer jlgznum;

	/**
	 * 停电查询
	 */
	private Integer tdcxnum;

	/**
	 * 其他
	 */
	private Integer qtnum;

	/**
	 * 保修合计
	 *
	 * @since 3.0.0-beta1
	 */
	private Integer bxhjnum;
	/**
	 * 每十万户报修率
	 *
	 * @since 3.0.0-beta1
	 */
	private Double mswhbxl;
	/**
	 * 固定十万户
	 *
	 * @since 3.0.0-beta1
	 */
	private Double gdswhbxl;
	/**
	 * 全市故障抢修平均到达现场时长
	 *
	 * @since 3.0.0-beta1
	 */
	private Double pjddxcscfz;
	/**
	 * 全市平均修复时长
	 *
	 * @since 3.0.0-beta1
	 */
	private Double pjgzxfscfz;

	/**
	 * 总计
	 */
	private String zj;
}
