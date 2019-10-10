package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAll();

    void add(Meal meal);

    void delete(int id);

    void update(Meal meal);

    Meal getById(int id);
}