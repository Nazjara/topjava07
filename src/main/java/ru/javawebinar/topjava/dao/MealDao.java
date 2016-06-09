package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Nazar on 06.06.2016.
 */
public interface MealDao
{
    void createMeal(UserMeal user);
    void updateMeal(UserMeal user);
    UserMealWithExceed getMealById(long id);
    List<UserMealWithExceed> getMealsByDate(LocalDateTime startTime, LocalDateTime endTime);
    List<UserMealWithExceed> getAllMeals();
    void deleteMeal(long id);
}
