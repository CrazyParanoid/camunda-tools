package io.github.camunda.tools.process;

import io.github.camunda.tools.values.TestValues;
import org.springframework.stereotype.Service;

@Service
public class ApplicationTestService {

    @StartProcess(businessKey = "getBusinessKey()", processKey = TestValues.PROCESS_KEY)
    public TestApplication testApplication() {
        return new TestApplication(TestValues.BUSINESS_KEY);
    }

}
