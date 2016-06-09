package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoImplWithMap;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Nazar on 04.06.2016.
 */
public class MealServlet extends HttpServlet
{
    private static final Logger LOG = getLogger(MealServlet.class);
    private static String LIST_MEAL = "/mealList.jsp";
    private MealDaoImplWithMap dao;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public MealServlet()
    {
        super();
        dao = new MealDaoImplWithMap();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        LOG.debug("redirect to mealList");
        String forward;
        if(request.getParameter("action") == null)
        {
            forward = LIST_MEAL;
            request.setAttribute("meals", dao.getAllMeals());
        }
        else
        {
            String action = request.getParameter("action");
            if (action.equalsIgnoreCase("delete")) {
                long mealId = Long.parseLong(request.getParameter("mealId"));
                dao.deleteMeal(mealId);
                forward = LIST_MEAL;
                request.setAttribute("meals", dao.getAllMeals());
            } else if (action.equalsIgnoreCase("edit")) {
                forward = LIST_MEAL;
                long mealId = Long.parseLong(request.getParameter("mealId"));
                UserMealWithExceed meal = dao.getMealById(mealId);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("mealList")) {
                forward = LIST_MEAL;
                request.setAttribute("meals", dao.getAllMeals());
            } else {
                forward = LIST_MEAL;
                request.setAttribute("meals", dao.getAllMeals());
            }
        }
        getServletContext().getRequestDispatcher(forward).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getParameter("startDate") != null && request.getParameter("endDate") != null)
        {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if(startDate.isEmpty() && endDate.isEmpty())
                request.setAttribute("meals",dao.getAllMeals());
            else if(startDate.isEmpty())
                request.setAttribute("meals",dao.getMealsByDate(LocalDateTime.MIN,LocalDateTime.parse(endDate.replace(" ", "T"),formatter)));
            else if(endDate.isEmpty())
                request.setAttribute("meals",dao.getMealsByDate(LocalDateTime.parse(startDate.replace(" ", "T"),formatter),LocalDateTime.MAX));
            else
                request.setAttribute("meals",dao.getMealsByDate(LocalDateTime.parse(startDate.replace(" ", "T"),formatter),LocalDateTime.parse(endDate.replace(" ", "T"),formatter)));
            getServletContext().getRequestDispatcher(LIST_MEAL).forward(request,response);
        }
        else
        {
            request.setCharacterEncoding("UTF-8");
            UserMeal meal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime").replace(" ", "T"), formatter), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            String mealId = request.getParameter("mealId");
            if (mealId == null || mealId.isEmpty())
                dao.createMeal(meal);
            else {
                meal.setId_1(Integer.parseInt(mealId));
                dao.updateMeal(meal);
            }
            request.setAttribute("meals", dao.getAllMeals());
            getServletContext().getRequestDispatcher(LIST_MEAL).forward(request, response);
        }
    }
}
