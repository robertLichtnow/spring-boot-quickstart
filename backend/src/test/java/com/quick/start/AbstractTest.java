package com.quick.start;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TestConfig.class,
        Application.class
})
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        listeners = {
                DbUnitTestExecutionListener.class
        })
public abstract class AbstractTest {
}