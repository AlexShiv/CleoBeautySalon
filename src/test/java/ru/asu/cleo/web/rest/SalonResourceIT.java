package ru.asu.cleo.web.rest;

import ru.asu.cleo.CleoBeautySalonApp;
import ru.asu.cleo.domain.Salon;
import ru.asu.cleo.repository.SalonRepository;
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
import java.util.List;

import static ru.asu.cleo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SalonResource} REST controller.
 */
@SpringBootTest(classes = CleoBeautySalonApp.class)
public class SalonResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private SalonRepository salonRepository;

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

    private MockMvc restSalonMockMvc;

    private Salon salon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalonResource salonResource = new SalonResource(salonRepository);
        this.restSalonMockMvc = MockMvcBuilders.standaloneSetup(salonResource)
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
    public static Salon createEntity(EntityManager em) {
        Salon salon = new Salon()
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE);
        return salon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salon createUpdatedEntity(EntityManager em) {
        Salon salon = new Salon()
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        return salon;
    }

    @BeforeEach
    public void initTest() {
        salon = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalon() throws Exception {
        int databaseSizeBeforeCreate = salonRepository.findAll().size();

        // Create the Salon
        restSalonMockMvc.perform(post("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isCreated());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeCreate + 1);
        Salon testSalon = salonList.get(salonList.size() - 1);
        assertThat(testSalon.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSalon.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createSalonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salonRepository.findAll().size();

        // Create the Salon with an existing ID
        salon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalonMockMvc.perform(post("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isBadRequest());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSalons() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        // Get all the salonList
        restSalonMockMvc.perform(get("/api/salons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salon.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getSalon() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        // Get the salon
        restSalonMockMvc.perform(get("/api/salons/{id}", salon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salon.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingSalon() throws Exception {
        // Get the salon
        restSalonMockMvc.perform(get("/api/salons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalon() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        int databaseSizeBeforeUpdate = salonRepository.findAll().size();

        // Update the salon
        Salon updatedSalon = salonRepository.findById(salon.getId()).get();
        // Disconnect from session so that the updates on updatedSalon are not directly saved in db
        em.detach(updatedSalon);
        updatedSalon
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);

        restSalonMockMvc.perform(put("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalon)))
            .andExpect(status().isOk());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeUpdate);
        Salon testSalon = salonList.get(salonList.size() - 1);
        assertThat(testSalon.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSalon.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalon() throws Exception {
        int databaseSizeBeforeUpdate = salonRepository.findAll().size();

        // Create the Salon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalonMockMvc.perform(put("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isBadRequest());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalon() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        int databaseSizeBeforeDelete = salonRepository.findAll().size();

        // Delete the salon
        restSalonMockMvc.perform(delete("/api/salons/{id}", salon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
