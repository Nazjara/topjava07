package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private UserMealRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(UserMealRestController.class);
    }

    @Override
    public void destroy()
    {
        appCtx.close();
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if(request.getParameter("startDate") != null && request.getParameter("endDate") != null)
        {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if(startDate.isEmpty() && endDate.isEmpty())
                request.setAttribute("mealList",controller.getAll());
            else if(startDate.isEmpty())
                request.setAttribute("mealList",controller.getAllByDate(LocalDateTime.MIN,LocalDateTime.parse(endDate.replace("T"," "),TimeUtil.DATE_TME_FORMATTER)));
            else if(endDate.isEmpty())
                request.setAttribute("mealList",controller.getAllByDate(LocalDateTime.parse(startDate.replace("T", " "),TimeUtil.DATE_TME_FORMATTER),LocalDateTime.MAX));
            else
                request.setAttribute("mealList",controller.getAllByDate(LocalDateTime.parse(startDate.replace("T", " "),TimeUtil.DATE_TME_FORMATTER),LocalDateTime.parse(endDate.replace("T"," "),TimeUtil.DATE_TME_FORMATTER)));
            getServletContext().getRequestDispatcher("/mealList.jsp").forward(request,response);
        }
        else
        {
            String id = request.getParameter("id");
            UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")), LoggedUser.getId());
            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            controller.create(userMeal);
            response.sendRedirect("meals");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000,LoggedUser.getId()) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
