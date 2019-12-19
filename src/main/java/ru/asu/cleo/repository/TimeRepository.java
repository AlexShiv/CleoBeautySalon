package ru.asu.cleo.repository;
import ru.asu.cleo.domain.Time;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Time entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

}
