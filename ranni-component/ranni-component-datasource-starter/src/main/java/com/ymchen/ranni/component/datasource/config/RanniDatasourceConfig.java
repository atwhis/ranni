package com.ymchen.ranni.component.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RanniDatasourceConfig {


    /**
     * https://baomidou.com/pages/2976a3/#spring-boot
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new DataPermissionInterceptor()); //TODO 数据权限
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 分页插件
        return interceptor;
    }

    /*@Bean
    public MybatisConfiguration mybatisConfiguration(){
        final MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setUseDeprecatedExecutor(false);
        return mybatisConfiguration;
    }*/
}
