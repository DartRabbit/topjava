package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(value = {Profiles.DATAJPA})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void testGetWithMeal() throws Exception {
        User user = service.get(USER_ID);
        Comparator<Meal> comparator = Comparator.comparing(Meal::getDateTime).reversed();
        List<Meal> meals = user.getMeals();
        meals.sort(comparator);
        assertMatch(user, USER);
        assertMatch(meals, MEALS);
    }
}
