package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.CommonUserTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.POSTGRES,Profiles.DATAJPA})
public class UserServiceTest extends CommonUserTest
{
    @Test
    public void testGetWithMeals() throws Exception
    {
        User actualUser = userService.getWithMeals(USER_ID);
        Set<UserMeal> actualSet = actualUser.getMeals();
        MATCHER_USER.assertEquals(USER, actualUser);
        Set<UserMeal> resultSet = new HashSet<>(mealService.getAll(USER_ID));
        assertEquals(actualSet,resultSet);
    }
}