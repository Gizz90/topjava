package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        final User withMeals = getWithMeals(USER_ID);
        UserTestData.assertMatch(withMeals, USER);
        MealTestData.assertMatch(withMeals.getMeals(), MEALS);
    }

    @Test
    public void getWithEmptyMeals() {
        User newUser = service.create(new User(
                null, "New", "new@gmail.com", "newPass",
                1555, false, new Date(), Collections.singleton(Role.ROLE_USER)));
        final Integer id = newUser.getId();
        final User withMeals = getWithMeals(newUser.getId());
        UserTestData.assertMatch(withMeals, newUser);
        MealTestData.assertMatch(withMeals.getMeals(), Collections.emptyList());
    }

    private User getWithMeals(Integer id) {
        return service.getWithMeals(id);
    }
}
