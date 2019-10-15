package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(String startDate, String startTime, String endDate, String endTime) {
        log.info("getAllFiltered");
        return service.getAllFiltered(authUserId(),
                (startDate == null || startDate.equals("")) ? LocalDate.MIN : LocalDate.parse(startDate),
                (startTime == null || startTime.equals("")) ? LocalTime.MIN : LocalTime.parse(startTime),
                (endDate == null || endDate.equals("")) ? LocalDate.MAX : LocalDate.parse(endDate),
                (endTime == null || endTime.equals("")) ? LocalTime.MAX : LocalTime.parse(endTime),
                authUserCaloriesPerDay());
    }
}