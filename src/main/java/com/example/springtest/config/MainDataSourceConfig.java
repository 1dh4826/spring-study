package com.example.springtest.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.springtest.**.maintenance.dao", sqlSessionFactoryRef = "mainSqlSessionFactory")
public class MainDataSourceConfig {

    // mapper接口所在包
    static final String PACKAGE = "com.exampls.springtest.**.maintenance.dao";

    // mapper映射文件对应的位置
    static final String MAPPER_LOCATION = "classpath*:com/exampls/springtest/**/maintenance/dao/*.xml";

    // 设置主库，主数据源
    @Bean(name = "mainDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.maintenance")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(
            @Qualifier("mainDataSource") DataSource mainDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mainDataSource);

        // java与xml放在同一包下，无需设置映射文件位置
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(MainDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "mainSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("mainSqlSessionFactory") SqlSessionFactory mainSqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(mainSqlSessionFactory);
    }


    @Bean(name = "mainMapperSxannerConfigure")
    public MapperScannerConfigurer masterTransactionManager() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("mainSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(PACKAGE);
        return mapperScannerConfigurer;
    }
}
