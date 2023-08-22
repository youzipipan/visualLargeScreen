// package com.pg.screen;
//
// import com.mybatisflex.codegen.Generator;
// import com.mybatisflex.codegen.config.GlobalConfig;
// import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
// import oracle.jdbc.pool.OracleDataSource;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import java.sql.SQLException;
// import java.sql.Timestamp;
// import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.Set;
//
// @SpringBootTest
// class DbCreateTests {
//
//     @Test
//     void create() throws SQLException {
//
//         // 配置数据源
//         OracleDataSource dataSource = new OracleDataSource();
//         dataSource.setURL("jdbc:oracle:thin:@114.115.152.117:57641:ORCLCDB");
//         dataSource.setUser("C##SHUJUFENXI");
//         dataSource.setPassword("Fmcyehd7OJtznDyC");
//         // 创建配置内容
//         GlobalConfig globalConfig = createGlobalConfig();
//         // 通过 datasource 和 globalConfig 创建代码生成器
//         Generator generator = new Generator(dataSource, globalConfig);
//         // 生成代码
//         generator.generate();
//     }
//
//     public static GlobalConfig createGlobalConfig() {
//
//         JdbcTypeMapping.registerMapping(Timestamp.class, LocalDateTime.class);
//         //JdbcTypeMapping.registerMapping(Timestamp.class, LocalDateTime.class);
//
//         // 创建配置内容
//         GlobalConfig globalConfig = new GlobalConfig();
//         // 设置根包
//         globalConfig
//                 .getPackageConfig()
//                 .setBasePackage("com.pg.screen")
//                 .setEntityPackage(
//                         globalConfig.getBasePackage()+".mapper.entity");
//         // 覆盖之前生成的文件
//         globalConfig.getEntityConfig().setOverwriteEnable(true);
//         // 设置生成 entity 并启用 Lombok
//         globalConfig
//                 .enableEntity()
//                 .setWithLombok(true);
//         // 设置生成 mapper
//         globalConfig.enableMapper();
//         // 注释配置
//         globalConfig.getJavadocConfig()
//                 .setAuthor("chuang.chen")
//                 .setTableCommentFormat(s -> s);
//         Set<String> tableSet = new HashSet<>();
//         tableSet.add("ACTIVE_REPAIR");
//         globalConfig.setGenerateTables(tableSet);
//         return globalConfig;
//     }
// }
//
