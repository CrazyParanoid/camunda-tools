package io.github.camunda.tools.process;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
    }

}
