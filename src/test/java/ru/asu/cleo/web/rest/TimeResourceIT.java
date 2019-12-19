package ru.asu.cleo.web.rest;

import ru.asu.cleo.CleoBeautySalonApp;
import ru.asu.cleo.domain.Time;
import ru.asu.cleo.repository.TimeRepository;
import ru.asu.cleo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ru.asu.cleo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TimeResource} REST controller.
 */
@SpringBootTest(classes = CleoBeautySalonApp.class)
public class TimeResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTimeMockMvc;

    private Time time;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeResource timeResource = new TimeResource(timeRepository);
        this.restTimeMockMvc = MockMvcBuilders.standaloneSetup(timeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Time createEntity(EntityManager em) {
        Time time = new Time()
            .date(DEFAULT_DATE)
            .phone(DEFAULT_PHONE);
        return time;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Time createUpdatedEntity(EntityManager em) {
        Time time = new Time()
            .date(UPDATED_DATE)
            .phone(UPDATED_PHONE);
        return time;
    }

    @BeforeEach
    public void initTest() {
        time = createEntity(em);
    }

    @Test
    @Transactional
    public void createTime() throws Exception {
        int databaseSizeBeforeCreate = timeRepository.findAll().size();

        // Create the Time
        restTimeMockMvc.perform(post("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isCreated());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate + 1);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTime.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeRepository.findAll().size();

        // Create the Time with an existing ID
        time.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeMockMvc.perform(post("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimes() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get all the timeList
        restTimeMockMvc.perform(get("/api/times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(time.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get the time
        restTimeMockMvc.perform(get("/api/times/{id}", time.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(time.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingTime() throws Exception {
        // Get the time
        restTimeMockMvc.perform(get("/api/times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Update the time
        Time updatedTime = timeRepository.findById(time.getId()).get();
        // Disconnect from session so that the updates on updatedTime are not directly saved in db
        em.detach(updatedTime);
        updatedTime
            .date(UPDATED_DATE)
            .phone(UPDATED_PHONE);

        restTimeMockMvc.perform(put("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTime)))
            .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTime.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Create the Time

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeMockMvc.perform(put("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeDelete = timeRepository.findAll().size();

        // Delete the time
        restTimeMockMvc.perform(delete("/api/times/{id}", time.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
