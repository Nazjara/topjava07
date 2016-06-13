package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nazar on 13.06.2016.
 */
public class UsersUtil
{
    public static final List<User> USER_LIST = Arrays.asList(
            new User("Nazjara","qwerty@gmail.com","qwerty123456", Role.ROLE_ADMIN),
            new User("Oksi","asdfgh@gmail.com","asdfgh123456", Role.ROLE_USER)
    );
}
