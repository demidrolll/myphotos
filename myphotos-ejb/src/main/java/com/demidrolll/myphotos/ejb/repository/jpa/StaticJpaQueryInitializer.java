package com.demidrolll.myphotos.ejb.repository.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Inject;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class StaticJpaQueryInitializer {

    @Inject
    private JpaRepositoryFinder jpaRepositoryFinder;

    @Inject
    private JpaQueryParser jpaQueryParser;

    @PostConstruct
    private void postConstruct() {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface JpaQuery {

        String name() default "";

        String value();
    }

    @Dependent
    public static class JpaRepositoryFinder {

        @Inject
        protected Logger logger;

        @Inject
        protected BeanManager beanManager;

        protected Class<?> getRepositoryClass() {
            return AbstractJpaRepository.class;
        }

        protected boolean isCandidateValid(Bean<?> bean) {
            return true;
        }

        public Set<Class<?>> getJpaRepositoryClasses() {
            Set<Class<?>> result = new HashSet<>();
            for (Bean<?> bean : beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {})) {
                Class<?> beanClass = bean.getBeanClass();
                if (isCandidateValid(bean) && getRepositoryClass().isAssignableFrom(beanClass)) {
                    result.add(beanClass);
                    logger.log(Level.INFO, "Found {0} JPA repository class", beanClass.getName());
                }
            }
            return result;
        }
    }

    @Dependent
    public static class JpaQueryParser {

        public Map<String, String> getNamedQueriesMap(Set<Class<?>> jpaRepositoryClasses) {
            Map<String, String> namedQueriesMap = new HashMap<>();
            for (Class<?> jpaRepositoryClass : jpaRepositoryClasses) {
                addQueriesFromJpaRepository(jpaRepositoryClass, namedQueriesMap);
            }
            return namedQueriesMap;
        }

        private void addQueriesFromJpaRepository(Class<?> jpaRepositoryClass, Map<String, String> namedQueriesMap) {
            for (Method method : MethodUtils.getMethodsWithAnnotation(jpaRepositoryClass, JpaQuery.class)) {

            }
        }
    }
}
