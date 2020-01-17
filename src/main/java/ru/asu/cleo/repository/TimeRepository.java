package ru.asu.cleo.repository;
import org.springframework.data.repository.query.Param;
import ru.asu.cleo.domain.Job;
import ru.asu.cleo.domain.Salon;
import ru.asu.cleo.domain.Time;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data  repository for the Time entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    @Query(value = "SELECT t FROM Time t WHERE t.salon = :salon and t.job = :job and DATE_FORMAT(t.date, '%d-%m-%Y') = DATE_FORMAT(:date, '%d-%m-%Y')")
    List<Time> findAllBySalonAndJobAndDate(@Param("salon") Salon salon, @Param("job") Job job, @Param("date") Instant date);


}
