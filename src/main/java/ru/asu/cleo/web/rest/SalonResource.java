package ru.asu.cleo.web.rest;

import ru.asu.cleo.domain.Salon;
import ru.asu.cleo.repository.SalonRepository;
import ru.asu.cleo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.asu.cleo.domain.Salon}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SalonResource {

    private final Logger log = LoggerFactory.getLogger(SalonResource.class);

    private static final String ENTITY_NAME = "salon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalonRepository salonRepository;

    public SalonResource(SalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    /**
     * {@code POST  /salons} : Create a new salon.
     *
     * @param salon the salon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salon, or with status {@code 400 (Bad Request)} if the salon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salons")
    public ResponseEntity<Salon> createSalon(@RequestBody Salon salon) throws URISyntaxException {
        log.debug("REST request to save Salon : {}", salon);
        if (salon.getId() != null) {
            throw new BadRequestAlertException("A new salon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Salon result = salonRepository.save(salon);
        return ResponseEntity.created(new URI("/api/salons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salons} : Updates an existing salon.
     *
     * @param salon the salon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salon,
     * or with status {@code 400 (Bad Request)} if the salon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salons")
    public ResponseEntity<Salon> updateSalon(@RequestBody Salon salon) throws URISyntaxException {
        log.debug("REST request to update Salon : {}", salon);
        if (salon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Salon result = salonRepository.save(salon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salons} : get all the salons.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salons in body.
     */
    @GetMapping("/salons")
    public List<Salon> getAllSalons() {
        log.debug("REST request to get all Salons");
        return salonRepository.findAll();
    }

    /**
     * {@code GET  /salons/:id} : get the "id" salon.
     *
     * @param id the id of the salon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salons/{id}")
    public ResponseEntity<Salon> getSalon(@PathVariable Long id) {
        log.debug("REST request to get Salon : {}", id);
        Optional<Salon> salon = salonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(salon);
    }

    /**
     * {@code DELETE  /salons/:id} : delete the "id" salon.
     *
     * @param id the id of the salon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salons/{id}")
    public ResponseEntity<Void> deleteSalon(@PathVariable Long id) {
        log.debug("REST request to delete Salon : {}", id);
        salonRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
