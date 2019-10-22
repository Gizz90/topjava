package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), DINNER, LUNCH, BREAKFAST);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "new meal", 1000);
        service.create(newMeal, USER_ID);
        assertMatch(service.getAll(USER_ID), newMeal, DINNER, LUNCH, BREAKFAST);
    }

    @Test(expected = DuplicateKeyException.class)
    public void createWithSameDateTime() {
        Meal meal = new Meal(LocalDateTime.of(2019, 10, 16, 13, 00, 00), "UserLunch", 1100);
        service.create(meal, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(DINNER.getId(), USER_ID);
        assertMatch(meal, DINNER);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        assertMatch(service.get(BREAKFAST.getId(), ADMIN_ID), BREAKFAST);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(BREAKFAST.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), DINNER, LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(BREAKFAST.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test
    public void update() {
        Meal updated = BREAKFAST;
        updated.setDescription("updatedBreakfast");
        updated.setCalories(2000);
        service.update(updated, USER_ID);
        assertMatch(updated, service.get(BREAKFAST.getId(), USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updated = BREAKFAST;
        updated.setDescription("updatedBreakfast");
        updated.setCalories(2000);
        service.update(updated, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(BREAKFAST, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2019, 10, 16),
                LocalDate.of(2019, 10, 17),
                USER_ID
        );
        assertMatch(meals, DINNER, LUNCH);
    }
}