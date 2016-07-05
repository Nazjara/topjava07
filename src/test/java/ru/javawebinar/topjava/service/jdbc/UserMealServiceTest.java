package ru.javawebinar.topjava.service.jdbc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.CommonUserMealTest;

import java.util.concurrent.TimeUnit;

@ActiveProfiles({Profiles.POSTGRES,Profiles.JDBC})
public class UserMealServiceTest extends CommonUserMealTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceTest.class);

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        private void logInfo(Description description, long nanos) {
            LOG.info(String.format("+++ Test %s spent %d ms",
                    description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, nanos);
        }
    };

    @Test(expected = UnsupportedOperationException.class)
    public void testGetWithUser() throws Exception
    {
        throw new UnsupportedOperationException();
    }
}