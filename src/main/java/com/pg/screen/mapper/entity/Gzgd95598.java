package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 故障工单95598(Gzgd95598)实体类
 *
 * @author makejava
 * @since 2023-06-07 15:20:26
 */
@Data
@Table(value = "GZGD95598")
public class Gzgd95598 implements Serializable {

    private static final long serialVersionUID = -26713930768322290L;

    private Integer id;
    /**
     * 工单编号
     */
    private String gdbh;
    /**
     * 国网工单编号
     */
    private String gwgdbh;
    /**
     * 所属地市
     */
    private String ssds;
    /**
     * 工单单位
     */
    private String gddw;
    /**
     * 故障地址
     */
    private String gzdz;
    /**
     * 受理时间
     */
    private Date slsj;
    /**
     * 故障描述
     */
    private String gzms;
    /**
     * 故障类型
     */
    private String gzlx;
    /**
     * 用户编号
     */
    private String yhbh;
    /**
     * 客户编号（回填）
     */
    private String khbhht;
    /**
     * 客户表号
     */
    private String khbh;
    /**
     * 用户信息
     */
    private String yhxx;
    /**
     * 用户类型
     */
    private String yhlx;
    /**
     * 是否敏感用户
     */
    private String sfmgyh;
    /**
     * 是否超时
     */
    private String sfcs;
    /**
     * 工单来源
     */
    private String gdly;
    /**
     * 抢修人员
     */
    private String qxry;
    /**
     * 抢修车辆
     */
    private String qxcl;
    /**
     * 抢修班组
     */
    private String qxbz;
    /**
     * 处理情况
     */
    private String clqk;
    /**
     * 转派记录
     */
    private String zpjl;
    /**
     * 回单人员
     */
    private String hdry;
    /**
     * 工单状态
     */
    private String gdzt;
    /**
     * app状态
     */
    private String appzt;
    /**
     * 是否子单
     */
    private String sfzd;
    /**
     * 主单编号
     */
    private String zdbh;
    /**
     * 派工用时(分钟)
     */
    private String pgysfz;
    /**
     * 所属线路
     */
    private String ssxl;
    /**
     * 所属台区
     */
    private String sstq;
    /**
     * 到达现场时长(分钟)
     */
    private String ddxcscfz;
    /**
     * 故障修复时长(分钟)
     */
    private String gzxfscfz;
    /**
     * 归档时间
     */
    private Date gdsj;
    /**
     * 抢修现场记录
     */
    private String qxxcjl;
    /**
     * 研判结果
     */
    private String ypjg;
    /**
     * 故障大类
     */
    private String gzdl;
    /**
     * 故障小类1
     */
    private String gzxly;
    /**
     * 故障小类2
     */
    private String gzxle;
    /**
     * 故障原因大类
     */
    private String gzyydl;
    /**
     * 故障原因小类
     */
    private String gzyyxl;
    /**
     * 是否督办
     */
    private String sfdb;
    /**
     * 现场分类
     */
    private String xcfl;
    /**
     * 设备产权属性
     */
    private String sbcqsx;

}

