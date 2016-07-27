package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id=:id")
    User findOne(@Param("id")Integer id);

    @Override
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
    List<User> findAll();

    @EntityGraph(value = User.GRAPH_WITH_ROLES)
    User getByEmail(String email);

    @EntityGraph(value = User.GRAPH_WITH_ROLES_AND_MEALS)
    User findById(Integer id);
}