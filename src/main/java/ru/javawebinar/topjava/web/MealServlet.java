package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.MockDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        MockDB db = MockDB.getInstance();

        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(db.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}
