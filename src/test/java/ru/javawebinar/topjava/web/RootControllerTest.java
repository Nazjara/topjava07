package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 10.04.2015.
 */
public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUserList() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("userList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/userList.jsp"))
                .andExpect(model().attribute("userList", hasSize(2)))
                .andExpect(model().attribute("userList", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }
    @Test
    public void testMealList() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList",hasSize(6)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("id",is(START_SEQ + 2)),
                                hasProperty("description",is(MEAL1.getDescription())),
                                hasProperty("dateTime",is(MEAL1.getDateTime())),
                                hasProperty("calories",is(MEAL1.getCalories()))
                        )
                )));
    }
}