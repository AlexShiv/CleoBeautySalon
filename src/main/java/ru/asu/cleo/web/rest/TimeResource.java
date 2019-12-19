package ru.asu.cleo.web.rest;

import ru.asu.cleo.domain.Time;
import ru.asu.cleo.repository.TimeRepository;
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
 * REST controller for managing {@link ru.asu.cleo.domain.Time}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class);

    private static final String ENTITY_NAME = "time";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeRepository timeRepository;

    public TimeResource(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    /**
     * {@code POST  /times} : Create a new time.
     *
     * @param time the time to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new time, or with status {@code 400 (Bad Request)} if the time has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to save Time : {}", time);
        if (time.getId() != null) {
            throw new BadRequestAlertException("A new time cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Time result = timeRepository.save(time);
        return ResponseEntity.created(new URI("/api/times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /times} : Updates an existing time.
     *
     * @param time the time to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated time,
     * or with status {@code 400 (Bad Request)} if the time is not valid,
     * or with status {@code 500 (Internal Server Error)} if the time couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/times")
    public ResponseEntity<Time> updateTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to update Time : {}", time);
        if (time.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Time result = timeRepository.save(time);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, time.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /times} : get all the times.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of times in body.
     */
    @GetMapping("/times")
    public List<Time> getAllTimes() {
        log.debug("REST request to get all Times");
        return timeRepository.findAll();
    }

    /**
     * {@code GET  /times/:id} : get the "id" time.
     *
     * @param id the id of the time to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the time, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/times/{id}")
    public ResponseEntity<Time> getTime(@PathVariable Long id) {
        log.debug("REST request to get Time : {}", id);
        Optional<Time> time = timeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(time);
    }

    /**
     * {@code DELETE  /times/:id} : delete the "id" time.
     *
     * @param id the id of the time to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        log.debug("REST request to delete Time : {}", id);
        timeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
