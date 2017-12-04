package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
        //@Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int deleteByIdAndUserId(int id, int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<List<Meal>> findAllByUserId(int userId, Sort sort);


    Optional<Meal> findByIdAndUserId(int id, int userId);


    Optional<List<Meal>> findAllByUserIdAndDateTimeBetween(int userId, LocalDateTime startDT, LocalDateTime endDT, Sort sort);
}
