package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService
{
    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id,int userID) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id,userID),id);
    }

    @Override
    public UserMeal get(int id,int userId) {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id,userId),id);
    }

    @Override
    public void update(UserMeal userMeal, int userId) {
        repository.save(userMeal);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }
}
