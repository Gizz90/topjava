package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.*;

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
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededStreamed(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededOneLoop(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededWithRecursion(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededOneStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println("****************************************************");
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
        System.out.println(getFilteredWithExceededStreamed(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
        System.out.println(getFilteredWithExceededOneLoop(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
        System.out.println(getFilteredWithExceededWithRecursion(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
        System.out.println(getFilteredWithExceededOneStream(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();
        mealList.forEach(meal -> dailyCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        dailyCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                );
                result.add(userMealWithExceed);
            }
        });
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStreamed(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = mealList.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        dailyCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOneLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> mealPerDay = new HashMap<>();
        mealList.forEach(meal -> mealPerDay.computeIfAbsent(meal.getLocalDate(), v -> new ArrayList<>()).add(meal));

        List<UserMealWithExceed> result = new ArrayList<>();
        mealPerDay.values().forEach(valueList -> {
            int sum = 0;
            for (UserMeal meal : valueList) {
                sum += meal.getCalories();
            }
            boolean isExceeded = sum > caloriesPerDay;
            valueList.forEach(meal -> {
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(meal.getDateTime(),
                            meal.getDescription(),
                            meal.getCalories(),
                            isExceeded);
                    result.add(userMealWithExceed);
                }
            });
        });
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOneStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> mealPerDay = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.toList()));
        return mealPerDay.values().stream()
                .flatMap(valueList -> {
                    boolean isExceeded = valueList.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
                    return valueList.stream()
                            .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> new UserMealWithExceed(meal.getDateTime(),
                                    meal.getDescription(),
                                    meal.getCalories(),
                                    isExceeded));
                })
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> dailyCaloriesMap;

    public static List<UserMealWithExceed> getFilteredWithExceededWithRecursion(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        dailyCaloriesMap = new HashMap<>();
        return filterWithRecursion(mealList, startTime, endTime,caloriesPerDay, 0);
    }

    private static List<UserMealWithExceed> filterWithRecursion(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay, int index) {
        List<UserMealWithExceed> result = new ArrayList<>();
        UserMeal meal = mealList.get(index);
        dailyCaloriesMap.merge(meal.getLocalDate(), meal.getCalories(), Integer::sum);
        if (index + 1 < mealList.size()) {
            result.addAll(filterWithRecursion(mealList, startTime, endTime, caloriesPerDay, index + 1));
        }
        if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
            UserMealWithExceed userMealWithExceed = new UserMealWithExceed(meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    dailyCaloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
            );
            result.add(userMealWithExceed);
        }
        return result;
    }
}