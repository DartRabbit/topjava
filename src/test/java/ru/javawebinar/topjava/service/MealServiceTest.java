package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(ADMIN_MEAL_1.getId(), ADMIN_ID);
        assertMatch(meal, ADMIN_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ADMIN_MEAL_1.getId(), ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 29);
        LocalDate endDate = LocalDate.of(2015, Month.MAY, 30);
        List<Meal> meals = service.getBetweenDates(startDate, endDate, USER_ID);
        assertMatch(meals, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 29);
        LocalDate endDate = LocalDate.of(2015, Month.MAY, 30);
        LocalTime startTime = LocalTime.of(7, 0, 0);
        LocalTime endTime = LocalTime.of(14, 0, 0);
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), USER_ID);
        assertMatch(meals, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(ADMIN_MEAL_1);
        updated.setCalories(300);
        updated.setDescription("Админ завтрак");
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 1, 7, 0));
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL_1.getId(), ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(ADMIN_MEAL_1);
        updated.setCalories(300);
        updated.setDescription("Админ завтрак");
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 1, 7, 0));
        service.update(updated, USER_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 7, 0), "Админ завтрак", 300);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_2, ADMIN_MEAL_1, newMeal);
    }

}