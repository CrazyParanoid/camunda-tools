package io.github.camunda.tools.values.beans;

import io.github.camunda.tools.delegate.Delegate;
import io.github.camunda.tools.process.ProcessVariable;
import io.github.camunda.tools.values.TestOutputObject;
import io.github.camunda.tools.values.TestValues;

public class TestBeanWithOutputVariableFunctionCall {

    @Delegate(beanName = TestValues.TEST_DELEGATE_FIRST_NAME, key = TestValues.PROCESS_KEY,
            variables = {@ProcessVariable(
                    name = TestValues.CLASS_NAME_VARIABLE,
                    value = TestValues.CLASS_NAME_VARIABLE_EXPRESSION
            )})
    public TestOutputObject doAction(String processKey) {
        return new TestOutputObject(TestValues.TEST_OUTPUT_OBJECT_VARIABLE_RESULT);
    }
}
