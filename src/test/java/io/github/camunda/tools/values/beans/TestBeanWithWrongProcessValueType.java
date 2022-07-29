package io.github.camunda.tools.values.beans;

import io.github.camunda.tools.delegate.Delegate;
import io.github.camunda.tools.process.ProcessValue;
import io.github.camunda.tools.values.TestOutputObject;
import io.github.camunda.tools.values.TestValues;

public class TestBeanWithWrongProcessValueType {

    @Delegate(beanName = TestValues.TEST_DELEGATE_FIRST_NAME, key = TestValues.PROCESS_KEY)
    public TestOutputObject doAction(String processKey,
                                     @ProcessValue(
                                             value = TestValues.TEST_STRING_PROCESS_VALUE,
                                             type = Integer.class
                                     ) Integer variable) {
        return new TestOutputObject(TestValues.TEST_OUTPUT_OBJECT_VARIABLE_RESULT);
    }

}
