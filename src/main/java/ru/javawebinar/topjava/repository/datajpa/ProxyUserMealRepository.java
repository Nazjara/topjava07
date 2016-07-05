package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Nazar on 01.07.2016.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer>
{
    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:user_id")
    int delete(@Param("id") int id,@Param("user_id") int user_id);

    @Transactional
    @Override
    UserMeal save(UserMeal userMeal);

    @Query("SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:user_id")
    UserMeal findOne(@Param("id") int id,@Param("user_id") int user_id);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:user_id")
    List<UserMeal> findAll(@Param("user_id") int user_id,Sort sort);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:user_id AND um.dateTime BETWEEN :startDate AND :endDate ORDER BY um.dateTime DESC")
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("user_id") int user_id);

    @Query("SELECT um FROM UserMeal um LEFT JOIN FETCH um.user WHERE um.id=:id AND um.user.id=:user_id")
    UserMeal getWithUser(@Param("id") int id,@Param("user_id") int user_id);
}
