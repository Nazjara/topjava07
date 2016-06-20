package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Nazar on 20.06.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator populator;

    @Before
    public void setUp() throws Exception {
        populator.execute();
    }

    @Test
    public void testGet() throws Exception
    {
        UserMeal meal = service.get(NUMBER,USER_ID);
        MATCHER.assertEquals(MEAL1,meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(NUMBER+1,USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL1),service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1,MEAL2),service.getBetweenDates(LocalDate.of(2016,6,20),LocalDate.MAX,USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2),service.getBetweenDateTimes(LocalDateTime.of(2016,6,20,13,0),LocalDateTime.of(2016,6,20,15,0),USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL3),service.getAll(ADMIN_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        TestUserMeal updated = new TestUserMeal(NUMBER,LocalDateTime.of(2016,6,20,10,0),"Crunch",666);
        service.update(updated,USER_ID);
        MATCHER.assertEquals(updated, service.get(NUMBER,USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        TestUserMeal newMeal = new TestUserMeal(LocalDateTime.now(),"NewMeal",1234);
        UserMeal meal = service.save(newMeal,ADMIN_ID);
        newMeal.setId(meal.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3,newMeal),service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateOthers() throws Exception
    {
        TestUserMeal updated = new TestUserMeal(NUMBER,LocalDateTime.of(2016,6,20,10,0),"Crunch",666);
        service.update(updated,ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteOthers() throws Exception
    {
        service.delete(NUMBER+2,USER_ID);
    }
}