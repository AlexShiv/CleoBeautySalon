package ru.asu.cleo.web.rest;

import ru.asu.cleo.CleoBeautySalonApp;
import ru.asu.cleo.domain.Promo;
import ru.asu.cleo.repository.PromoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.asu.cleo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PromoResource} REST controller.
 */
@SpringBootTest(classes = CleoBeautySalonApp.class)
public class PromoResourceIT {

    private static final String DEFAULT_PROMO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROMO_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DESCRIPTION = 1L;
    private static final Long UPDATED_DESCRIPTION = 2L;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SALE = 1;
    private static final Integer UPDATED_SALE = 2;

    @Autowired
    private PromoRepository promoRepository;

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

    private MockMvc restPromoMockMvc;

    private Promo promo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PromoResource promoResource = new PromoResource(promoRepository);
        this.restPromoMockMvc = MockMvcBuilders.standaloneSetup(promoResource)
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
    public static Promo createEntity(EntityManager em) {
        Promo promo = new Promo()
            .promoName(DEFAULT_PROMO_NAME)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .sale(DEFAULT_SALE);
        return promo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promo createUpdatedEntity(EntityManager em) {
        Promo promo = new Promo()
            .promoName(UPDATED_PROMO_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .sale(UPDATED_SALE);
        return promo;
    }

    @BeforeEach
    public void initTest() {
        promo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromo() throws Exception {
        int databaseSizeBeforeCreate = promoRepository.findAll().size();

        // Create the Promo
        restPromoMockMvc.perform(post("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isCreated());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeCreate + 1);
        Promo testPromo = promoList.get(promoList.size() - 1);
        assertThat(testPromo.getPromoName()).isEqualTo(DEFAULT_PROMO_NAME);
        assertThat(testPromo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPromo.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPromo.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPromo.getSale()).isEqualTo(DEFAULT_SALE);
    }

    @Test
    @Transactional
    public void createPromoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = promoRepository.findAll().size();

        // Create the Promo with an existing ID
        promo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromoMockMvc.perform(post("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isBadRequest());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPromos() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        // Get all the promoList
        restPromoMockMvc.perform(get("/api/promos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promo.getId().intValue())))
            .andExpect(jsonPath("$.[*].promoName").value(hasItem(DEFAULT_PROMO_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].sale").value(hasItem(DEFAULT_SALE)));
    }
    
    @Test
    @Transactional
    public void getPromo() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        // Get the promo
        restPromoMockMvc.perform(get("/api/promos/{id}", promo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(promo.getId().intValue()))
            .andExpect(jsonPath("$.promoName").value(DEFAULT_PROMO_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.sale").value(DEFAULT_SALE));
    }

    @Test
    @Transactional
    public void getNonExistingPromo() throws Exception {
        // Get the promo
        restPromoMockMvc.perform(get("/api/promos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromo() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        int databaseSizeBeforeUpdate = promoRepository.findAll().size();

        // Update the promo
        Promo updatedPromo = promoRepository.findById(promo.getId()).get();
        // Disconnect from session so that the updates on updatedPromo are not directly saved in db
        em.detach(updatedPromo);
        updatedPromo
            .promoName(UPDATED_PROMO_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .sale(UPDATED_SALE);

        restPromoMockMvc.perform(put("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPromo)))
            .andExpect(status().isOk());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeUpdate);
        Promo testPromo = promoList.get(promoList.size() - 1);
        assertThat(testPromo.getPromoName()).isEqualTo(UPDATED_PROMO_NAME);
        assertThat(testPromo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPromo.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPromo.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPromo.getSale()).isEqualTo(UPDATED_SALE);
    }

    @Test
    @Transactional
    public void updateNonExistingPromo() throws Exception {
        int databaseSizeBeforeUpdate = promoRepository.findAll().size();

        // Create the Promo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromoMockMvc.perform(put("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isBadRequest());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePromo() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        int databaseSizeBeforeDelete = promoRepository.findAll().size();

        // Delete the promo
        restPromoMockMvc.perform(delete("/api/promos/{id}", promo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
