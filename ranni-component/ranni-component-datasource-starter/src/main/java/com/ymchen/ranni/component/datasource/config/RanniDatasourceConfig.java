package com.ymchen.ranni.component.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ymchen.ranni.component.datasource.inteceptor.DataPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RanniDatasourceConfig {


    /**
     * https://baomidou.com/pages/2976a3/#spring-boot
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 分页插件
        return interceptor;
    }

    /**
     * 注册数据权限
     */
    @Bean
    @Order(-1)
    public DataPermissionInterceptor dataPermissionInterceptor() {
        return new DataPermissionInterceptor();
    }

    /*@Bean
    public MybatisConfiguration mybatisConfiguration(){
        final MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setUseDeprecatedExecutor(false);
        return mybatisConfiguration;
    }*/
}
