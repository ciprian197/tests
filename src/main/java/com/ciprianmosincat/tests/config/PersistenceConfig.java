package com.ciprianmosincat.tests.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan("com.ciprianmosincat.tests.domain")
@EnableJpaRepositories("com.ciprianmosincat.tests.repository")
public class PersistenceConfig {

    @Bean
    public JPAQueryFactory queryFactory(final EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
