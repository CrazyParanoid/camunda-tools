package io.github.camunda.tools.values.beans;

import io.github.camunda.tools.delegate.Delegate;
import io.github.camunda.tools.delegate.Delegates;
import io.github.camunda.tools.values.TestValues;

public class TestBeanWithMultipleDelegate {

    @Delegates(
            values = {
                    @Delegate(beanName = TestValues.TEST_DELEGATE_FIRST_NAME, key = TestValues.PROCESS_KEY),
                    @Delegate(beanName = TestValues.TEST_DELEGATE_SECOND_NAME, key = TestValues.PROCESS_KEY)
            }
    )
    public String doAction(String processKey) {
        return processKey;
    }

}
