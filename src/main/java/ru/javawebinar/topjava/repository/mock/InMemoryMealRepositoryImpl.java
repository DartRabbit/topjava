package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.ADMIN_MEALS.forEach(meal -> save(1, meal));
        MealsUtil.USER_MEALS.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {} for user with id {}", meal, userId);
        if (!repository.containsKey(userId)) {
            repository.put(userId, new HashMap<>());
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete meal {} for user with id {}", id, userId);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal {} for user with id {}", id, userId);
        return repository.get(userId).getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all meal for user with id {}", userId);
        Comparator<Meal> comparator = Comparator.comparing(Meal::getDateTime).reversed();
        return repository.get(userId).values().stream().sorted(comparator).collect(Collectors.toList());
    }
}

