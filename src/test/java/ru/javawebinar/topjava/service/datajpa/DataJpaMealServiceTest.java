package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(value = {Profiles.DATAJPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUserTest() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        User user = actual.getUser();
        assertMatch(actual, ADMIN_MEAL1);
        assertMatch(user, ADMIN);
    }
}
