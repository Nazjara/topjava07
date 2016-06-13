package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500,1),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,1),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,1),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,1),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,1),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,1)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {

    }

    public static List<UserMealWithExceed> getWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, LocalDateTime.MIN, LocalDateTime.MAX, caloriesPerDay);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        mealList.forEach(meal -> caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            final LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime, startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        });
        return mealExceeded;
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(), exceeded);
    }
}