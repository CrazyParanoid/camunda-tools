package io.github.camunda.tools.delegate;

import io.github.camunda.tools.process.ProcessValuesDefiner;
import io.github.camunda.tools.process.ProcessVariable;
import io.github.camunda.tools.process.ProcessVariablesCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * The main component responsible for finding delegate methods in the bean and registering them in the spring context.
 */
@Component
class DelegatesBeanRegistrar {
    private final GenericApplicationContext applicationContext;
    private final ProcessValuesDefiner processValuesDefiner;

    private final Logger log = LoggerFactory.getLogger(DelegatesBeanRegistrar.class);

    @Autowired
    private DelegatesBeanRegistrar(GenericApplicationContext applicationContext, ProcessValuesDefiner processValuesDefiner) {
        this.applicationContext = applicationContext;
        this.processValuesDefiner = processValuesDefiner;
    }

    void registerDelegatesForBean(Object bean) {
        Method[] delegateMethods = bean.getClass().getMethods();

        for (Method delegateMethod : delegateMethods) {
            Delegate delegateAnnotation = AnnotationUtils.findAnnotation(delegateMethod, Delegate.class);
            Delegates delegatesAnnotation = AnnotationUtils.findAnnotation(delegateMethod, Delegates.class);

            registerDelegate(delegateMethod, bean, delegateAnnotation);
            registerDelegates(delegateMethod, bean, delegatesAnnotation);
        }
    }

    private void registerDelegates(Method delegateMethod, Object bean, Delegates delegatesAnnotation) {
        if (Objects.nonNull(delegatesAnnotation)) {
            Delegate[] delegates = delegatesAnnotation.values();
            Arrays.stream(delegates).forEach(delegate -> registerDelegate(delegateMethod, bean, delegate));
        }
    }

    private void registerDelegate(Method delegateMethod, Object bean, Delegate delegate) {
        if (Objects.nonNull(delegate)) {

            ProcessVariable[] processVariables = delegate.variables();
            String processKey = delegate.key();
            String beanName = delegate.beanName();
            Object[] processValues = processValuesDefiner.defineProcessValues(delegateMethod.getParameters());

            Invocation invocation = Invocation.newInvocation(delegateMethod, bean, processValues);
            Map<String, String> variables = Arrays.stream(processVariables).collect(ProcessVariablesCollector.toValuesMap());

            applicationContext.registerBean(beanName, GenericDelegate.class);

            postInitGenericDelegate(invocation, processKey, variables, beanName);
            log.info("{} has been registered", beanName);
        }
    }

    private void postInitGenericDelegate(Invocation invocation, String processKey,
                                         Map<String, String> variables, String beanName) {
        GenericDelegate genericDelegate = applicationContext.getBean(beanName, GenericDelegate.class);
        genericDelegate.setInvocation(invocation);
        genericDelegate.setProcessKeyName(processKey);
        genericDelegate.setVariables(variables);
    }
}
