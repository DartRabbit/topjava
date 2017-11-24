package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MockDB {
    private static MockDB instance;
    private static AtomicInteger counter = new AtomicInteger(1);
    private static ConcurrentHashMap<Integer, Meal> map = new ConcurrentHashMap<>();

    private MockDB() {
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        map.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static MockDB getInstance() {
        if (instance == null) {
            instance = new MockDB();
        }
        return instance;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}
