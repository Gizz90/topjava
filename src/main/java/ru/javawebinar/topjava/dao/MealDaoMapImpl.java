package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMapImpl implements MealDao {
    private static final Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private static AtomicInteger count = new AtomicInteger();

    static {
        map.put(1, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(2, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(3, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        map.put(4, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        map.put(5, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        map.put(6, new Meal(count.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void add(Meal meal) {
        int id = count.getAndIncrement();
        meal.setId(id);
        map.put(id, meal);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public void update(Meal meal) {
        map.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return map.get(id);
    }
}