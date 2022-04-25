package com.ymchen.rannibase.remote.fallback;

import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.remote.UserRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserRemoteServiceFallback implements FallbackFactory<UserRemoteService> {
    @Override
    public UserRemoteService create(Throwable throwable) {

        return new UserRemoteService() {
            @Override
            public List<User> getAllUser() {
                log.error("调用获取所有用户失败", throwable);
                return null;
            }

            @Override
            public User getById(Integer userId) {
                log.error("调用获取用户ID为:{} 的信息失败", userId, throwable);
                return null;
            }
        };
    }
}
