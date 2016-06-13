package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id,int userId)
    {
        LOG.info("delete " + id);
        if(!repository.containsKey(id) || repository.get(id).getUserId() != userId)
            return false;
        else {
            repository.remove(id);
            return true;
        }
    }

    @Override
    public UserMeal get(int id,int userId)
    {
        LOG.info("get " + id);
        if(!repository.containsKey(id) || repository.get(id).getUserId() != userId)
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId)
    {
        LOG.info("getAll");
        Collection<UserMeal> listWithUserMeal = repository.values();
        return listWithUserMeal.stream().filter(userMeal -> userMeal.getUserId() == userId).sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
    }
}

