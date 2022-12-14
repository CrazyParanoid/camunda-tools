package io.github.camunda.tools.process;

import io.github.camunda.tools.common.ExpressionExecutor;
import org.apache.commons.lang3.ArrayUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ProcessStarter {
    private final RuntimeService runtimeService;
    private final GenericApplicationContext applicationContext;

    @Autowired
    public ProcessStarter(RuntimeService runtimeService, GenericApplicationContext applicationContext) {
        this.runtimeService = runtimeService;
        this.applicationContext = applicationContext;
    }

    void startProcess(StartProcess startProcessAnnotation, Object result) {
        String processKeyValue = applicationContext.getBeanFactory()
                .resolveEmbeddedValue(Objects.requireNonNull(startProcessAnnotation).processKey());
        ProcessVariable[] processVariables = startProcessAnnotation.variables();

        String businessKey = (String) ExpressionExecutor.parseAndExecuteExpression(result, startProcessAnnotation.businessKey());

        Map<String, Object> variableValues = new HashMap<>();

        if (ArrayUtils.isNotEmpty(processVariables)) {
            Map<String, String> variables = Arrays.stream(processVariables).collect(ProcessVariablesCollector.toValuesMap());
            variableValues = ExpressionExecutor.parseAndExecuteExpressions(result, variables);
        }

        runtimeService.createProcessInstanceByKey(processKeyValue)
                .businessKey(businessKey)
                .setVariables(variableValues)
                .execute();
    }

}
