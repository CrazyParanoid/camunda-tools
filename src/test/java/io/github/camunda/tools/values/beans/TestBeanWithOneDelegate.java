package io.github.camunda.tools.values.beans;

import io.github.camunda.tools.delegate.Delegate;
import io.github.camunda.tools.values.TestValues;

public class TestBeanWithOneDelegate {

    @Delegate(beanName = TestValues.TEST_DELEGATE_FIRST_NAME, key = TestValues.PROCESS_KEY)
    public int doAction(String processKey, int param) {
        return ++param;
    }

}
