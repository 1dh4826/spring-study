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
@MapperScan(basePackages = "com.example.springtest.**.publish.dao", sqlSessionFactoryRef = "publishSqlSessionFactory")
public class PublishDataSourceConfig {

    // mapper接口所在包
    static final String PACKAGE = "com.exampls.springtest.**.publish.dao";

    // mapper映射文件对应的位置
    static final String MAPPER_LOCATION = "classpath*:com/exampls/springtest/**/publish/dao/*.xml";

    // 设置主库，主数据源
    @Bean(name = "publishDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.publish")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "publishSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(
            @Qualifier("publishDataSource") DataSource mainDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mainDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(PublishDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "publishSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("publishSqlSessionFactory") SqlSessionFactory publishSqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(publishSqlSessionFactory);
    }


    @Bean(name = "publishMapperSxannerConfigure")
    public MapperScannerConfigurer masterTransactionManager() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("publishSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(PACKAGE);
        return mapperScannerConfigurer;
    }
}
