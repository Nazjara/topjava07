package ru.javawebinar.topjava.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.UserMeal;

/**
 * Created by Nazar on 01.08.2016.
 */
@Component
public class UserMealValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz)
    {
        return UserMeal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserMeal meal = (UserMeal) o;
        Integer calories = meal.getCalories();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateTime", "meal.dateTime.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "meal.description.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"calories","meal.calories.empty");
        if (calories < 10 || calories > 5000)
            errors.rejectValue(calories.toString(), "meal.calories.moreThen5000OrLessThan10");
    }
}
