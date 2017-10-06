package com.coolcook.web.rest;

import com.coolcook.CoolcookApp;

import com.coolcook.domain.IngredientAtHome;
import com.coolcook.repository.IngredientAtHomeRepository;
import com.coolcook.service.IngredientAtHomeService;
import com.coolcook.repository.search.IngredientAtHomeSearchRepository;
import com.coolcook.service.dto.IngredientAtHomeDTO;
import com.coolcook.service.mapper.IngredientAtHomeMapper;
import com.coolcook.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.coolcook.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IngredientAtHomeResource REST controller.
 *
 * @see IngredientAtHomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class IngredientAtHomeResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private IngredientAtHomeRepository ingredientAtHomeRepository;

    @Autowired
    private IngredientAtHomeMapper ingredientAtHomeMapper;

    @Autowired
    private IngredientAtHomeService ingredientAtHomeService;

    @Autowired
    private IngredientAtHomeSearchRepository ingredientAtHomeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientAtHomeMockMvc;

    private IngredientAtHome ingredientAtHome;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IngredientAtHomeResource ingredientAtHomeResource = new IngredientAtHomeResource(ingredientAtHomeService);
        this.restIngredientAtHomeMockMvc = MockMvcBuilders.standaloneSetup(ingredientAtHomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientAtHome createEntity(EntityManager em) {
        IngredientAtHome ingredientAtHome = new IngredientAtHome()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .expiryDate(DEFAULT_EXPIRY_DATE);
        return ingredientAtHome;
    }

    @Before
    public void initTest() {
        ingredientAtHomeSearchRepository.deleteAll();
        ingredientAtHome = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientAtHome() throws Exception {
        int databaseSizeBeforeCreate = ingredientAtHomeRepository.findAll().size();

        // Create the IngredientAtHome
        IngredientAtHomeDTO ingredientAtHomeDTO = ingredientAtHomeMapper.toDto(ingredientAtHome);
        restIngredientAtHomeMockMvc.perform(post("/api/ingredient-at-homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAtHomeDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientAtHome in the database
        List<IngredientAtHome> ingredientAtHomeList = ingredientAtHomeRepository.findAll();
        assertThat(ingredientAtHomeList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientAtHome testIngredientAtHome = ingredientAtHomeList.get(ingredientAtHomeList.size() - 1);
        assertThat(testIngredientAtHome.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testIngredientAtHome.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testIngredientAtHome.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);

        // Validate the IngredientAtHome in Elasticsearch
        IngredientAtHome ingredientAtHomeEs = ingredientAtHomeSearchRepository.findOne(testIngredientAtHome.getId());
        assertThat(ingredientAtHomeEs).isEqualToComparingFieldByField(testIngredientAtHome);
    }

    @Test
    @Transactional
    public void createIngredientAtHomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientAtHomeRepository.findAll().size();

        // Create the IngredientAtHome with an existing ID
        ingredientAtHome.setId(1L);
        IngredientAtHomeDTO ingredientAtHomeDTO = ingredientAtHomeMapper.toDto(ingredientAtHome);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientAtHomeMockMvc.perform(post("/api/ingredient-at-homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAtHomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IngredientAtHome> ingredientAtHomeList = ingredientAtHomeRepository.findAll();
        assertThat(ingredientAtHomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIngredientAtHomes() throws Exception {
        // Initialize the database
        ingredientAtHomeRepository.saveAndFlush(ingredientAtHome);

        // Get all the ingredientAtHomeList
        restIngredientAtHomeMockMvc.perform(get("/api/ingredient-at-homes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientAtHome.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))));
    }

    @Test
    @Transactional
    public void getIngredientAtHome() throws Exception {
        // Initialize the database
        ingredientAtHomeRepository.saveAndFlush(ingredientAtHome);

        // Get the ingredientAtHome
        restIngredientAtHomeMockMvc.perform(get("/api/ingredient-at-homes/{id}", ingredientAtHome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientAtHome.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientAtHome() throws Exception {
        // Get the ingredientAtHome
        restIngredientAtHomeMockMvc.perform(get("/api/ingredient-at-homes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientAtHome() throws Exception {
        // Initialize the database
        ingredientAtHomeRepository.saveAndFlush(ingredientAtHome);
        ingredientAtHomeSearchRepository.save(ingredientAtHome);
        int databaseSizeBeforeUpdate = ingredientAtHomeRepository.findAll().size();

        // Update the ingredientAtHome
        IngredientAtHome updatedIngredientAtHome = ingredientAtHomeRepository.findOne(ingredientAtHome.getId());
        updatedIngredientAtHome
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .expiryDate(UPDATED_EXPIRY_DATE);
        IngredientAtHomeDTO ingredientAtHomeDTO = ingredientAtHomeMapper.toDto(updatedIngredientAtHome);

        restIngredientAtHomeMockMvc.perform(put("/api/ingredient-at-homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAtHomeDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientAtHome in the database
        List<IngredientAtHome> ingredientAtHomeList = ingredientAtHomeRepository.findAll();
        assertThat(ingredientAtHomeList).hasSize(databaseSizeBeforeUpdate);
        IngredientAtHome testIngredientAtHome = ingredientAtHomeList.get(ingredientAtHomeList.size() - 1);
        assertThat(testIngredientAtHome.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testIngredientAtHome.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testIngredientAtHome.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);

        // Validate the IngredientAtHome in Elasticsearch
        IngredientAtHome ingredientAtHomeEs = ingredientAtHomeSearchRepository.findOne(testIngredientAtHome.getId());
        assertThat(ingredientAtHomeEs).isEqualToComparingFieldByField(testIngredientAtHome);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientAtHome() throws Exception {
        int databaseSizeBeforeUpdate = ingredientAtHomeRepository.findAll().size();

        // Create the IngredientAtHome
        IngredientAtHomeDTO ingredientAtHomeDTO = ingredientAtHomeMapper.toDto(ingredientAtHome);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientAtHomeMockMvc.perform(put("/api/ingredient-at-homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAtHomeDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientAtHome in the database
        List<IngredientAtHome> ingredientAtHomeList = ingredientAtHomeRepository.findAll();
        assertThat(ingredientAtHomeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientAtHome() throws Exception {
        // Initialize the database
        ingredientAtHomeRepository.saveAndFlush(ingredientAtHome);
        ingredientAtHomeSearchRepository.save(ingredientAtHome);
        int databaseSizeBeforeDelete = ingredientAtHomeRepository.findAll().size();

        // Get the ingredientAtHome
        restIngredientAtHomeMockMvc.perform(delete("/api/ingredient-at-homes/{id}", ingredientAtHome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ingredientAtHomeExistsInEs = ingredientAtHomeSearchRepository.exists(ingredientAtHome.getId());
        assertThat(ingredientAtHomeExistsInEs).isFalse();

        // Validate the database is empty
        List<IngredientAtHome> ingredientAtHomeList = ingredientAtHomeRepository.findAll();
        assertThat(ingredientAtHomeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIngredientAtHome() throws Exception {
        // Initialize the database
        ingredientAtHomeRepository.saveAndFlush(ingredientAtHome);
        ingredientAtHomeSearchRepository.save(ingredientAtHome);

        // Search the ingredientAtHome
        restIngredientAtHomeMockMvc.perform(get("/api/_search/ingredient-at-homes?query=id:" + ingredientAtHome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientAtHome.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientAtHome.class);
        IngredientAtHome ingredientAtHome1 = new IngredientAtHome();
        ingredientAtHome1.setId(1L);
        IngredientAtHome ingredientAtHome2 = new IngredientAtHome();
        ingredientAtHome2.setId(ingredientAtHome1.getId());
        assertThat(ingredientAtHome1).isEqualTo(ingredientAtHome2);
        ingredientAtHome2.setId(2L);
        assertThat(ingredientAtHome1).isNotEqualTo(ingredientAtHome2);
        ingredientAtHome1.setId(null);
        assertThat(ingredientAtHome1).isNotEqualTo(ingredientAtHome2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientAtHomeDTO.class);
        IngredientAtHomeDTO ingredientAtHomeDTO1 = new IngredientAtHomeDTO();
        ingredientAtHomeDTO1.setId(1L);
        IngredientAtHomeDTO ingredientAtHomeDTO2 = new IngredientAtHomeDTO();
        assertThat(ingredientAtHomeDTO1).isNotEqualTo(ingredientAtHomeDTO2);
        ingredientAtHomeDTO2.setId(ingredientAtHomeDTO1.getId());
        assertThat(ingredientAtHomeDTO1).isEqualTo(ingredientAtHomeDTO2);
        ingredientAtHomeDTO2.setId(2L);
        assertThat(ingredientAtHomeDTO1).isNotEqualTo(ingredientAtHomeDTO2);
        ingredientAtHomeDTO1.setId(null);
        assertThat(ingredientAtHomeDTO1).isNotEqualTo(ingredientAtHomeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingredientAtHomeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingredientAtHomeMapper.fromId(null)).isNull();
    }
}
