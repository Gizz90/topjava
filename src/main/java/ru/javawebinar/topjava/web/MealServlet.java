package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private MealDao mealDao = new MealDaoMapImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("dtf", dtf);

        String action = request.getParameter("action");

        switch (action + "") {
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                log.debug("input parameters action {} id {}", action, idEdit);
                Meal meal = mealDao.getById(idEdit);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "add":
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "delete":
                int idDel = Integer.parseInt(request.getParameter("id"));
                log.debug("input parameters action {} id {}", action, idDel);
                mealDao.delete(idDel);
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            default:
                List<MealTo> meals = MealsUtil.getFiltered(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost executed");
        request.setCharacterEncoding("UTF-8");

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDate.parse(request.getParameter("date")).atTime(LocalTime.parse(request.getParameter("time")));

        log.debug("String {} {} {}", description, dateTime, calories);

        Meal meal = new Meal(0, dateTime, description, calories);

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            mealDao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDao.update(meal);
        }
        response.sendRedirect("meals");
    }
}