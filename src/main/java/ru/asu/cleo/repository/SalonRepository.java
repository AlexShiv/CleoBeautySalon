package ru.asu.cleo.repository;
import ru.asu.cleo.domain.Salon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Salon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {

}
