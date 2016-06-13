package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.LoggedUser;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    private final int userId;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this(null, dateTime, description, calories, exceed);
    }

    public UserMealWithExceed(Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.userId = LoggedUser.getId();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}
