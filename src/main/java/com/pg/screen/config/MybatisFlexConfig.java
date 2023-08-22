package com.pg.screen.config;

import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flex全局配置
 *
 * @author c.chuang
 */
@Configuration
public class MybatisFlexConfig {

    private static final Logger logger = LoggerFactory.getLogger("mybatis-flex-sql");

    @Bean
    public void auditManager() {
        // 开启审计功能
        AuditManager.setAuditEnable(true);
        AuditManager.setMessageCollector(
                auditMessage -> logger.info("{},{}ms",
                        auditMessage.getFullSql(),
                        auditMessage.getElapsedTime()
                )
        );
    }
}
