package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MockMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MockMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        if (action == null) {
            log.info("getAll");
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(new ArrayList<>(repository.getAll()), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            switch (action) {
                case "update": {
                    int id = getId(request);
                    log.info("try to update {}", id);
                    request.setAttribute("meal", repository.get(id));
                    request.getRequestDispatcher("meal.jsp").forward(request, response);
                    break;
                }
                case "create":
                    log.info("try to create");
                    request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 1000));
                    request.getRequestDispatcher("meal.jsp").forward(request, response);
                    break;
                case "delete": {
                    int id = getId(request);
                    log.info("delete {}", id);
                    repository.delete(id);
                    response.sendRedirect("meals");
                    break;
                }
                default:
                    log.info("getAll");
                    request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(new ArrayList<>(repository.getAll()), LocalTime.MIN, LocalTime.MAX, 2000));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(
                id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        log.info(meal.isNew() ? "created {}" : "updated {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String param = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(param);
    }
}
