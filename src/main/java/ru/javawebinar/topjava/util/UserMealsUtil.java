package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil
{

    public static void main(String[] args)
    {
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay, um.getId_1()))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        mealList.forEach(meal -> caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            final LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime, startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay, meal.getId_1()));
            }
        });
        return mealExceeded;
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded, long id) {
        return new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), exceeded, id);
    }
}