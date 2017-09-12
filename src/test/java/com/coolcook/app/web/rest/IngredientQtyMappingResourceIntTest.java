package com.coolcook.app.web.rest;

import com.coolcook.app.CoolcookApp;

import com.coolcook.app.domain.IngredientQtyMapping;
import com.coolcook.app.repository.IngredientQtyMappingRepository;
import com.coolcook.app.service.dto.IngredientQtyMappingDTO;
import com.coolcook.app.service.mapper.IngredientQtyMappingMapper;
import com.coolcook.app.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IngredientQtyMappingResource REST controller.
 *
 * @see IngredientQtyMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class IngredientQtyMappingResourceIntTest {

    @Autowired
    private IngredientQtyMappingRepository ingredientQtyMappingRepository;

    @Autowired
    private IngredientQtyMappingMapper ingredientQtyMappingMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientQtyMappingMockMvc;

    private IngredientQtyMapping ingredientQtyMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IngredientQtyMappingResource ingredientQtyMappingResource = new IngredientQtyMappingResource(ingredientQtyMappingRepository, ingredientQtyMappingMapper);
        this.restIngredientQtyMappingMockMvc = MockMvcBuilders.standaloneSetup(ingredientQtyMappingResource)
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
    public static IngredientQtyMapping createEntity(EntityManager em) {
        IngredientQtyMapping ingredientQtyMapping = new IngredientQtyMapping();
        return ingredientQtyMapping;
    }

    @Before
    public void initTest() {
        ingredientQtyMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientQtyMapping() throws Exception {
        int databaseSizeBeforeCreate = ingredientQtyMappingRepository.findAll().size();

        // Create the IngredientQtyMapping
        IngredientQtyMappingDTO ingredientQtyMappingDTO = ingredientQtyMappingMapper.toDto(ingredientQtyMapping);
        restIngredientQtyMappingMockMvc.perform(post("/api/ingredient-qty-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientQtyMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientQtyMapping in the database
        List<IngredientQtyMapping> ingredientQtyMappingList = ingredientQtyMappingRepository.findAll();
        assertThat(ingredientQtyMappingList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientQtyMapping testIngredientQtyMapping = ingredientQtyMappingList.get(ingredientQtyMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createIngredientQtyMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientQtyMappingRepository.findAll().size();

        // Create the IngredientQtyMapping with an existing ID
        ingredientQtyMapping.setId(1L);
        IngredientQtyMappingDTO ingredientQtyMappingDTO = ingredientQtyMappingMapper.toDto(ingredientQtyMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientQtyMappingMockMvc.perform(post("/api/ingredient-qty-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientQtyMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IngredientQtyMapping> ingredientQtyMappingList = ingredientQtyMappingRepository.findAll();
        assertThat(ingredientQtyMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIngredientQtyMappings() throws Exception {
        // Initialize the database
        ingredientQtyMappingRepository.saveAndFlush(ingredientQtyMapping);

        // Get all the ingredientQtyMappingList
        restIngredientQtyMappingMockMvc.perform(get("/api/ingredient-qty-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientQtyMapping.getId().intValue())));
    }

    @Test
    @Transactional
    public void getIngredientQtyMapping() throws Exception {
        // Initialize the database
        ingredientQtyMappingRepository.saveAndFlush(ingredientQtyMapping);

        // Get the ingredientQtyMapping
        restIngredientQtyMappingMockMvc.perform(get("/api/ingredient-qty-mappings/{id}", ingredientQtyMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientQtyMapping.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientQtyMapping() throws Exception {
        // Get the ingredientQtyMapping
        restIngredientQtyMappingMockMvc.perform(get("/api/ingredient-qty-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientQtyMapping() throws Exception {
        // Initialize the database
        ingredientQtyMappingRepository.saveAndFlush(ingredientQtyMapping);
        int databaseSizeBeforeUpdate = ingredientQtyMappingRepository.findAll().size();

        // Update the ingredientQtyMapping
        IngredientQtyMapping updatedIngredientQtyMapping = ingredientQtyMappingRepository.findOne(ingredientQtyMapping.getId());
        IngredientQtyMappingDTO ingredientQtyMappingDTO = ingredientQtyMappingMapper.toDto(updatedIngredientQtyMapping);

        restIngredientQtyMappingMockMvc.perform(put("/api/ingredient-qty-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientQtyMappingDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientQtyMapping in the database
        List<IngredientQtyMapping> ingredientQtyMappingList = ingredientQtyMappingRepository.findAll();
        assertThat(ingredientQtyMappingList).hasSize(databaseSizeBeforeUpdate);
        IngredientQtyMapping testIngredientQtyMapping = ingredientQtyMappingList.get(ingredientQtyMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientQtyMapping() throws Exception {
        int databaseSizeBeforeUpdate = ingredientQtyMappingRepository.findAll().size();

        // Create the IngredientQtyMapping
        IngredientQtyMappingDTO ingredientQtyMappingDTO = ingredientQtyMappingMapper.toDto(ingredientQtyMapping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientQtyMappingMockMvc.perform(put("/api/ingredient-qty-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientQtyMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientQtyMapping in the database
        List<IngredientQtyMapping> ingredientQtyMappingList = ingredientQtyMappingRepository.findAll();
        assertThat(ingredientQtyMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientQtyMapping() throws Exception {
        // Initialize the database
        ingredientQtyMappingRepository.saveAndFlush(ingredientQtyMapping);
        int databaseSizeBeforeDelete = ingredientQtyMappingRepository.findAll().size();

        // Get the ingredientQtyMapping
        restIngredientQtyMappingMockMvc.perform(delete("/api/ingredient-qty-mappings/{id}", ingredientQtyMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientQtyMapping> ingredientQtyMappingList = ingredientQtyMappingRepository.findAll();
        assertThat(ingredientQtyMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientQtyMapping.class);
        IngredientQtyMapping ingredientQtyMapping1 = new IngredientQtyMapping();
        ingredientQtyMapping1.setId(1L);
        IngredientQtyMapping ingredientQtyMapping2 = new IngredientQtyMapping();
        ingredientQtyMapping2.setId(ingredientQtyMapping1.getId());
        assertThat(ingredientQtyMapping1).isEqualTo(ingredientQtyMapping2);
        ingredientQtyMapping2.setId(2L);
        assertThat(ingredientQtyMapping1).isNotEqualTo(ingredientQtyMapping2);
        ingredientQtyMapping1.setId(null);
        assertThat(ingredientQtyMapping1).isNotEqualTo(ingredientQtyMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientQtyMappingDTO.class);
        IngredientQtyMappingDTO ingredientQtyMappingDTO1 = new IngredientQtyMappingDTO();
        ingredientQtyMappingDTO1.setId(1L);
        IngredientQtyMappingDTO ingredientQtyMappingDTO2 = new IngredientQtyMappingDTO();
        assertThat(ingredientQtyMappingDTO1).isNotEqualTo(ingredientQtyMappingDTO2);
        ingredientQtyMappingDTO2.setId(ingredientQtyMappingDTO1.getId());
        assertThat(ingredientQtyMappingDTO1).isEqualTo(ingredientQtyMappingDTO2);
        ingredientQtyMappingDTO2.setId(2L);
        assertThat(ingredientQtyMappingDTO1).isNotEqualTo(ingredientQtyMappingDTO2);
        ingredientQtyMappingDTO1.setId(null);
        assertThat(ingredientQtyMappingDTO1).isNotEqualTo(ingredientQtyMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingredientQtyMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingredientQtyMappingMapper.fromId(null)).isNull();
    }
}
