package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int NUMBER = BaseEntity.START_SEQ+2;
    public static final UserMeal MEAL1 = new UserMeal(NUMBER, LocalDateTime.of(2016,6,20,10,0),"Breakfast",250);
    public static final UserMeal MEAL2 = new UserMeal(NUMBER+1, LocalDateTime.of(2016,6,20,14,0),"Dinner",450);
    public static final UserMeal MEAL3 = new UserMeal(NUMBER+2, LocalDateTime.of(2016,6,20,19,0),"Supper",200);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static class TestUserMeal extends UserMeal
    {
        public TestUserMeal(LocalDateTime dateTime, String description, int calories)
        {
            super(dateTime,description,calories);
        }

        public TestUserMeal(Integer id,LocalDateTime dateTime, String description, int calories)
        {
            super(id,dateTime,description,calories);
        }
    }
}
