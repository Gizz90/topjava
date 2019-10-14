package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            User admin = adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println("\t" + admin);
            User user = adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_USER));
            System.out.println("\t" + user);

            MealService mealService = appCtx.getBean(MealService.class);
            Meal meal = mealService.create(new Meal(LocalDateTime.now(), "food", 500), user.getId());
            // mealService.update(meal, admin1.getId());

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.now(), "food", 500));
            mealRestController.create(new Meal(LocalDateTime.now(), "food", 1500));

            mealRestController.getAll().forEach(System.out::println);

            mealRestController.update(new Meal(LocalDateTime.now(), "food", 2000), 9);

            mealRestController.getAll().forEach(System.out::println);

            mealService.getAll(user.getId(), user.getCaloriesPerDay()).forEach(System.out::println);
        }
    }
}
