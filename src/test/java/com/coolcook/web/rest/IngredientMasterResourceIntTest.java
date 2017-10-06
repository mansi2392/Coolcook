package com.coolcook.web.rest;

import com.coolcook.CoolcookApp;

import com.coolcook.domain.IngredientMaster;
import com.coolcook.repository.IngredientMasterRepository;
import com.coolcook.service.IngredientMasterService;
import com.coolcook.repository.search.IngredientMasterSearchRepository;
import com.coolcook.service.dto.IngredientMasterDTO;
import com.coolcook.service.mapper.IngredientMasterMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coolcook.domain.enumeration.UnitOfQuantity;
/**
 * Test class for the IngredientMasterResource REST controller.
 *
 * @see IngredientMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class IngredientMasterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final UnitOfQuantity DEFAULT_UNIT = UnitOfQuantity.KILOGRAM;
    private static final UnitOfQuantity UPDATED_UNIT = UnitOfQuantity.GRAM;

    private static final Double DEFAULT_DEFAULT_QTY = 1D;
    private static final Double UPDATED_DEFAULT_QTY = 2D;

    @Autowired
    private IngredientMasterRepository ingredientMasterRepository;

    @Autowired
    private IngredientMasterMapper ingredientMasterMapper;

    @Autowired
    private IngredientMasterService ingredientMasterService;

    @Autowired
    private IngredientMasterSearchRepository ingredientMasterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientMasterMockMvc;

    private IngredientMaster ingredientMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IngredientMasterResource ingredientMasterResource = new IngredientMasterResource(ingredientMasterService);
        this.restIngredientMasterMockMvc = MockMvcBuilders.standaloneSetup(ingredientMasterResource)
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
    public static IngredientMaster createEntity(EntityManager em) {
        IngredientMaster ingredientMaster = new IngredientMaster()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .unit(DEFAULT_UNIT)
            .defaultQty(DEFAULT_DEFAULT_QTY);
        return ingredientMaster;
    }

    @Before
    public void initTest() {
        ingredientMasterSearchRepository.deleteAll();
        ingredientMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientMaster() throws Exception {
        int databaseSizeBeforeCreate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster
        IngredientMasterDTO ingredientMasterDTO = ingredientMasterMapper.toDto(ingredientMaster);
        restIngredientMasterMockMvc.perform(post("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientMaster testIngredientMaster = ingredientMasterList.get(ingredientMasterList.size() - 1);
        assertThat(testIngredientMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredientMaster.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testIngredientMaster.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testIngredientMaster.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testIngredientMaster.getDefaultQty()).isEqualTo(DEFAULT_DEFAULT_QTY);

        // Validate the IngredientMaster in Elasticsearch
        IngredientMaster ingredientMasterEs = ingredientMasterSearchRepository.findOne(testIngredientMaster.getId());
        assertThat(ingredientMasterEs).isEqualToComparingFieldByField(testIngredientMaster);
    }

    @Test
    @Transactional
    public void createIngredientMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster with an existing ID
        ingredientMaster.setId(1L);
        IngredientMasterDTO ingredientMasterDTO = ingredientMasterMapper.toDto(ingredientMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMasterMockMvc.perform(post("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIngredientMasters() throws Exception {
        // Initialize the database
        ingredientMasterRepository.saveAndFlush(ingredientMaster);

        // Get all the ingredientMasterList
        restIngredientMasterMockMvc.perform(get("/api/ingredient-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].defaultQty").value(hasItem(DEFAULT_DEFAULT_QTY.doubleValue())));
    }

    @Test
    @Transactional
    public void getIngredientMaster() throws Exception {
        // Initialize the database
        ingredientMasterRepository.saveAndFlush(ingredientMaster);

        // Get the ingredientMaster
        restIngredientMasterMockMvc.perform(get("/api/ingredient-masters/{id}", ingredientMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.defaultQty").value(DEFAULT_DEFAULT_QTY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientMaster() throws Exception {
        // Get the ingredientMaster
        restIngredientMasterMockMvc.perform(get("/api/ingredient-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientMaster() throws Exception {
        // Initialize the database
        ingredientMasterRepository.saveAndFlush(ingredientMaster);
        ingredientMasterSearchRepository.save(ingredientMaster);
        int databaseSizeBeforeUpdate = ingredientMasterRepository.findAll().size();

        // Update the ingredientMaster
        IngredientMaster updatedIngredientMaster = ingredientMasterRepository.findOne(ingredientMaster.getId());
        updatedIngredientMaster
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .unit(UPDATED_UNIT)
            .defaultQty(UPDATED_DEFAULT_QTY);
        IngredientMasterDTO ingredientMasterDTO = ingredientMasterMapper.toDto(updatedIngredientMaster);

        restIngredientMasterMockMvc.perform(put("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMasterDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeUpdate);
        IngredientMaster testIngredientMaster = ingredientMasterList.get(ingredientMasterList.size() - 1);
        assertThat(testIngredientMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredientMaster.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testIngredientMaster.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testIngredientMaster.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredientMaster.getDefaultQty()).isEqualTo(UPDATED_DEFAULT_QTY);

        // Validate the IngredientMaster in Elasticsearch
        IngredientMaster ingredientMasterEs = ingredientMasterSearchRepository.findOne(testIngredientMaster.getId());
        assertThat(ingredientMasterEs).isEqualToComparingFieldByField(testIngredientMaster);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster
        IngredientMasterDTO ingredientMasterDTO = ingredientMasterMapper.toDto(ingredientMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientMasterMockMvc.perform(put("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientMaster() throws Exception {
        // Initialize the database
        ingredientMasterRepository.saveAndFlush(ingredientMaster);
        ingredientMasterSearchRepository.save(ingredientMaster);
        int databaseSizeBeforeDelete = ingredientMasterRepository.findAll().size();

        // Get the ingredientMaster
        restIngredientMasterMockMvc.perform(delete("/api/ingredient-masters/{id}", ingredientMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ingredientMasterExistsInEs = ingredientMasterSearchRepository.exists(ingredientMaster.getId());
        assertThat(ingredientMasterExistsInEs).isFalse();

        // Validate the database is empty
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIngredientMaster() throws Exception {
        // Initialize the database
        ingredientMasterRepository.saveAndFlush(ingredientMaster);
        ingredientMasterSearchRepository.save(ingredientMaster);

        // Search the ingredientMaster
        restIngredientMasterMockMvc.perform(get("/api/_search/ingredient-masters?query=id:" + ingredientMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].defaultQty").value(hasItem(DEFAULT_DEFAULT_QTY.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientMaster.class);
        IngredientMaster ingredientMaster1 = new IngredientMaster();
        ingredientMaster1.setId(1L);
        IngredientMaster ingredientMaster2 = new IngredientMaster();
        ingredientMaster2.setId(ingredientMaster1.getId());
        assertThat(ingredientMaster1).isEqualTo(ingredientMaster2);
        ingredientMaster2.setId(2L);
        assertThat(ingredientMaster1).isNotEqualTo(ingredientMaster2);
        ingredientMaster1.setId(null);
        assertThat(ingredientMaster1).isNotEqualTo(ingredientMaster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientMasterDTO.class);
        IngredientMasterDTO ingredientMasterDTO1 = new IngredientMasterDTO();
        ingredientMasterDTO1.setId(1L);
        IngredientMasterDTO ingredientMasterDTO2 = new IngredientMasterDTO();
        assertThat(ingredientMasterDTO1).isNotEqualTo(ingredientMasterDTO2);
        ingredientMasterDTO2.setId(ingredientMasterDTO1.getId());
        assertThat(ingredientMasterDTO1).isEqualTo(ingredientMasterDTO2);
        ingredientMasterDTO2.setId(2L);
        assertThat(ingredientMasterDTO1).isNotEqualTo(ingredientMasterDTO2);
        ingredientMasterDTO1.setId(null);
        assertThat(ingredientMasterDTO1).isNotEqualTo(ingredientMasterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingredientMasterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingredientMasterMapper.fromId(null)).isNull();
    }
}
