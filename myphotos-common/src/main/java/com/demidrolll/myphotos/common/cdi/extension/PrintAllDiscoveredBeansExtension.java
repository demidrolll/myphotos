package com.demidrolll.myphotos.common.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessProducer;
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
