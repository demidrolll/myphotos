package com.demidrolll.myphotos.ejb.repository.jpa;

import org.apache.commons.lang3.reflect.MethodUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    @Inject
    private JpaQueryCreator jpaQueryCreator;

    @PostConstruct
    private void postConstruct() {
        Set<Class<?>> jpaRepositoryClasses = jpaRepositoryFinder.getJpaRepositoryClasses();
        Map<String, String> namedQueriesMap = jpaQueryParser.getNamedQueriesMap(jpaRepositoryClasses);
        jpaQueryCreator.addAllNamedQueries(namedQueriesMap);
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

        protected void addQueriesFromJpaRepository(Class<?> jpaRepositoryClass, Map<String, String> namedQueriesMap) {
            for (Method method : MethodUtils.getMethodsWithAnnotation(jpaRepositoryClass, JpaQuery.class)) {
                JpaQuery jpaQuery = method.getAnnotation(JpaQuery.class);
                String key = jpaQuery.name();
                if (key.isEmpty()) {
                    key = String.format("%s.%s", getEntityClass(jpaRepositoryClass), method.getName());
                }
                String query = jpaQuery.value();
                if (namedQueriesMap.put(key, query) != null) {
                    throw new IllegalStateException("Detected named query duplicates: " + key);
                }
            }
        }

        protected String getEntityClass(Class<?> jpaRepositoryClass) {
            Type type = jpaRepositoryClass;
            while (type != null) {
                if (type instanceof ParameterizedType) {
                    return ((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0]).getSimpleName();
                }
                type = ((Class<?>) type).getGenericSuperclass();
            }
            throw new IllegalArgumentException("JPA class " + jpaRepositoryClass + " is not generic class");
        }
    }

    @Dependent
    public static class JpaQueryCreator {

        @Inject
        protected Logger logger;

        @PersistenceUnit
        protected EntityManagerFactory entityManagerFactory;

        protected void addAllNamedQueries(Map<String, String> namedQueriesMap) {
            EntityManager em = entityManagerFactory.createEntityManager();
            try {
                namedQueriesMap.forEach((key, value) -> {
                    entityManagerFactory.addNamedQuery(key, em.createQuery(value));
                    logger.log(Level.FINE, "Added named query: {0} -> {1}", new Object[]{key, value});
                });
            } finally {
                em.close();
            }
        }
    }
}
