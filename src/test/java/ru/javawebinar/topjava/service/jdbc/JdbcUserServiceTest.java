package ru.javawebinar.topjava.service.jdbc;

import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }
}