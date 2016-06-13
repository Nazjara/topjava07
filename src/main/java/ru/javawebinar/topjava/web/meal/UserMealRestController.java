package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    @Autowired
    private UserMealService service;
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll");
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.getId()),UserMealsUtil.DEFAULT_CALORIES_PER_DAY)
                .isEmpty() ? Collections.emptyList() : UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.getId()),UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<UserMealWithExceed> getAllByDate(LocalDateTime startDate,LocalDateTime endDate) {
        LOG.info("getAllByDate");
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(LoggedUser.getId()),startDate,endDate,UserMealsUtil.DEFAULT_CALORIES_PER_DAY)
                .isEmpty() ? Collections.emptyList() : UserMealsUtil.getFilteredWithExceeded(service.getAll(LoggedUser.getId()),startDate,endDate,UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id,LoggedUser.getId());
    }

    public UserMeal create(UserMeal userMeal) {
        userMeal.setId(null);
        LOG.info("create " + userMeal);
        return service.save(userMeal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id,LoggedUser.getId());
    }

    public void update(UserMeal userMeal) {
        LOG.info("update " + userMeal);
        service.update(userMeal,LoggedUser.getId());
    }
}
