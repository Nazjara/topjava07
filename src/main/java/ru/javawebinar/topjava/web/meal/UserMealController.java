package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Nazar on 09.07.2016.
 */
    @Controller
    public class UserMealController extends UserMealRestController
    {
        @RequestMapping(value = "/meals", method = RequestMethod.POST)
        public String postMethod(HttpServletRequest request,Model model)
        {
            if(request.getParameter("action")==null)
                return create(request);
            LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
            LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
            LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
            LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
            model.addAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));
            return "mealList";
        }

        public String create(HttpServletRequest request) {
            final UserMeal userMeal = new UserMeal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            if (request.getParameter("id").isEmpty()) {
                LOG.info("Create {}", userMeal);
                super.create(userMeal);
            } else {
                LOG.info("Update {}", userMeal);
                super.update(userMeal, getId(request));
            }
            return "redirect:meals";
        }

        @RequestMapping(value = "/meals", method = RequestMethod.GET)
        public String getMethod(Model model,HttpServletRequest request) {
            if(request.getParameter("action")==null)
                return getAll(model);
            String param = request.getParameter("action");
            switch (param) {
                case "delete": return delete(request);
                case "create": return create(model);
                case "update": return update(request,model);
                default: return getAll(model);
            }
        }

        public String create(Model model)
        {
            final UserMeal meal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000);
            model.addAttribute("meal", meal);
            return "mealEdit";
        }

        public String update(HttpServletRequest request,Model model) {
            final UserMeal meal =  super.get(getId(request));
            model.addAttribute("meal", meal);
            return "mealEdit";
        }

        public String getAll(Model model) {
            LOG.info("getAll");
            model.addAttribute("mealList", super.getAll());
            return "mealList";
        }

        public String delete(HttpServletRequest request) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            super.delete(id);
            return "redirect:meals";
        }

        private int getId(HttpServletRequest request) {
            String paramId = Objects.requireNonNull(request.getParameter("id"));
            return Integer.valueOf(paramId);
        }

        private String resetParam(String param, HttpServletRequest request) {
            String value = request.getParameter(param);
            request.setAttribute(param, value);
            return value;
        }
    }
