package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal {
    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    public static long id = 1;
    public long id_1 = id;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id++;
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

    public long getId_1() {
        return id_1;
    }

    public void setId_1(long id_1) {
        this.id_1 = id_1;
    }
}
