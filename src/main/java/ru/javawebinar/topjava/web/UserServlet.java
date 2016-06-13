package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private AdminRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(AdminRestController.class);
    }

    @Override
    public void destroy()
    {
        appCtx.close();
        super.destroy();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to userList");
        request.setAttribute("userList",controller.getAll());
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
        //response.sendRedirect("userList.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        LOG.info("Enter with user with name: {}", name);
        LoggedUser.setId(Integer.parseInt(id));
        response.sendRedirect("meals");
    }
}
