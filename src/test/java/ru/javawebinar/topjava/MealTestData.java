package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int BREAKFAST_ID = START_SEQ + 2;
    private static final int LUNCH_ID = START_SEQ + 3;
    private static final int DINNER_ID = START_SEQ + 4;

    public static final Meal USER_BREAKFAST = new Meal(BREAKFAST_ID, LocalDateTime.of(2019, 10, 15, 10, 00, 00), "UserBreakfast", 500);
    public static final Meal USER_LUNCH = new Meal(LUNCH_ID, LocalDateTime.of(2019, 10, 16, 13, 00, 00), "UserLunch", 1100);
    public static final Meal USER_DINNER = new Meal(DINNER_ID, LocalDateTime.of(2019, 10, 17, 20, 00, 00), "UserDinner", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
