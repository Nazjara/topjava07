package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
@Profile("datajpa")
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    private static final Sort SORT_DATE_TIME = new Sort(Sort.Direction.DESC,"dateTime");

    @Autowired
    private ProxyUserMealRepository proxy;

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserMeal save(UserMeal userMeal, int userId)
    {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }
        userMeal.setUser(em.getReference(User.class, userId));
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId)
    {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId)
    {
        return proxy.findOne(id,userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(userId,SORT_DATE_TIME);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId)
    {
        List<UserMeal> list =  proxy.getBetween(startDate,endDate,userId);
        list.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return list;
    }

    @Override
    public UserMeal getWithUser(int id,int userId)
    {
        return proxy.getWithUser(id,userId);
    }
}
