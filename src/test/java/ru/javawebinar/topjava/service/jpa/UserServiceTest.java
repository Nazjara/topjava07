package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.CommonUserTest;

@ActiveProfiles({Profiles.POSTGRES,Profiles.JPA})
public class UserServiceTest extends CommonUserTest
{
    @Test(expected = UnsupportedOperationException.class)
    public void testGetWithMeals() throws Exception
    {
        throw new UnsupportedOperationException();
    }
}