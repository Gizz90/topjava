package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 1500));
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21, 0), 1500));
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(12, 0), LocalTime.of(21, 0), 1500));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();
        for (UserMeal meal: mealList) {
            LocalDate key = meal.getDateTime().toLocalDate();
            dailyCalories.put(key, dailyCalories.getOrDefault(key, 0) + meal.getCalories());
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(meal.getDateTime()
                        , meal.getDescription()
                        , meal.getCalories()
                        , dailyCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                );
                result.add(userMealWithExceed);
            }
        }
        return result;
    }
}