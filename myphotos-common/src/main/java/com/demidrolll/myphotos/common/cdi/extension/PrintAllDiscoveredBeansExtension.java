package com.demidrolll.myphotos.common.cdi.extension;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessAnnotatedType;
import jakarta.enterprise.inject.spi.ProcessBean;
import jakarta.enterprise.inject.spi.ProcessInjectionTarget;
import jakarta.enterprise.inject.spi.ProcessProducer;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.logging.Logger;

public class PrintAllDiscoveredBeansExtension implements Extension {

    private final Logger logger = Logger.getLogger(getClass().getName());

    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
        logger.info("beforeBeanDiscovery");
    }

    <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> processAnnotatedType, BeanManager bm) {
        String className = processAnnotatedType.getAnnotatedType().getJavaClass().getName();
        if (className.startsWith("com.demidrolll")) {
            Set<Annotation> annotations = processAnnotatedType.getAnnotatedType().getAnnotations();
            logger.info(String.format("Discovered bean of %s with annotations: %s", className, annotations));
        }
    }

    <T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> pit) {
        logger.info("ProcessInjectionTarget " + pit.getAnnotatedType().getJavaClass().getSimpleName());
    }

    <T, X> void processProducer(@Observes final ProcessProducer<T, X> pp) {
        logger.info("ProcessProducer " + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
    }

    <X> void processBean(@Observes final ProcessBean<X> pb) {
        logger.info("ProcessBean " + pb.getBean().getBeanClass().getSimpleName());
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
        logger.info("afterBeanDiscovery");
    }

    void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        logger.info("afterDeploymentValidation");
    }
}
