package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Nazar on 06.06.2016.
 */
public class MealDaoImplWithMap implements MealDao
{
    public Map<Long,UserMeal> mapWithMeals = new ConcurrentSkipListMap<>();
    private int CALORIES_PER_DAY = 2000;

    @Override
    public void createMeal(UserMeal meal)
    {
        mapWithMeals.put(meal.getId_1(),meal);
    }

    @Override
    public void updateMeal(UserMeal meal)
    {
        UserMeal.id--;
        mapWithMeals.put(meal.getId_1(),meal);
    }

    @Override
    public UserMealWithExceed getMealById(long id)
    {
        return UserMealsUtil.getFilteredWithExceeded(Collections.singletonList(mapWithMeals.get(id)), LocalDateTime.MIN,LocalDateTime.MAX,CALORIES_PER_DAY).get(0);
    }

    @Override
    public List<UserMealWithExceed> getMealsByDate(LocalDateTime startTime, LocalDateTime endTime)
    {
        List<UserMeal> listWithMeals = mapWithMeals.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        return UserMealsUtil.getFilteredWithExceeded(listWithMeals,startTime,endTime,CALORIES_PER_DAY);
    }

    @Override
    public List<UserMealWithExceed> getAllMeals()
    {
        List<UserMeal> listWithMeals = mapWithMeals.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        return UserMealsUtil.getFilteredWithExceeded(listWithMeals,LocalDateTime.MIN,LocalDateTime.MAX,CALORIES_PER_DAY);
    }

    public void setCALORIES_PER_DAY(int CALORIES_PER_DAY) {
        this.CALORIES_PER_DAY = CALORIES_PER_DAY;
    }

    @Override
    public void deleteMeal(long id)
    {
        mapWithMeals.remove(id);
    }
}
