package ru.javawebinar.topjava.testrules;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ExecutionTimeRule extends Stopwatch {

    private final Logger LOG = LoggerFactory.getLogger(ExecutionTimeRule.class);
    private final Map<String, Long> testsTiming = new HashMap<>();

    public void getStats() {
        LOG.info(
                testsTiming
                        .entrySet()
                        .stream()
                        .map(entry -> String.format("executing %s spent %s ms", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n"))
        );
    }

    @Override
    protected void finished(long nanos, Description description) {
        String method = description.getMethodName();
        long millis = nanos / 1000000;
        testsTiming.put(method, millis);
        LOG.info("executing {} spent {} ms", method, millis);
    }
}
