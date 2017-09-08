package com.coolcook.app.web.rest;

import com.coolcook.app.CoolcookApp;

import com.coolcook.app.domain.IngredientMaster;
import com.coolcook.app.repository.IngredientMasterRepository;
import com.coolcook.app.service.IngredientMasterService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IngredientMasterResource REST controller.
 *
 * @see IngredientMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class IngredientMasterResourceIntTest {

    private static final Integer DEFAULT_INGREDIENT_ID = 1;
    private static final Integer UPDATED_INGREDIENT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private IngredientMasterRepository ingredientMasterRepository;

    @Autowired
    private IngredientMasterService ingredientMasterService;

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
            .ingredientId(DEFAULT_INGREDIENT_ID)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return ingredientMaster;
    }

    @Before
    public void initTest() {
        ingredientMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientMaster() throws Exception {
        int databaseSizeBeforeCreate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster
        restIngredientMasterMockMvc.perform(post("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMaster)))
            .andExpect(status().isCreated());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientMaster testIngredientMaster = ingredientMasterList.get(ingredientMasterList.size() - 1);
        assertThat(testIngredientMaster.getIngredientId()).isEqualTo(DEFAULT_INGREDIENT_ID);
        assertThat(testIngredientMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredientMaster.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testIngredientMaster.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createIngredientMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster with an existing ID
        ingredientMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMasterMockMvc.perform(post("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMaster)))
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
            .andExpect(jsonPath("$.[*].ingredientId").value(hasItem(DEFAULT_INGREDIENT_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
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
            .andExpect(jsonPath("$.ingredientId").value(DEFAULT_INGREDIENT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
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
        ingredientMasterService.save(ingredientMaster);

        int databaseSizeBeforeUpdate = ingredientMasterRepository.findAll().size();

        // Update the ingredientMaster
        IngredientMaster updatedIngredientMaster = ingredientMasterRepository.findOne(ingredientMaster.getId());
        updatedIngredientMaster
            .ingredientId(UPDATED_INGREDIENT_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restIngredientMasterMockMvc.perform(put("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredientMaster)))
            .andExpect(status().isOk());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeUpdate);
        IngredientMaster testIngredientMaster = ingredientMasterList.get(ingredientMasterList.size() - 1);
        assertThat(testIngredientMaster.getIngredientId()).isEqualTo(UPDATED_INGREDIENT_ID);
        assertThat(testIngredientMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredientMaster.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testIngredientMaster.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredientMasterRepository.findAll().size();

        // Create the IngredientMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientMasterMockMvc.perform(put("/api/ingredient-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientMaster)))
            .andExpect(status().isCreated());

        // Validate the IngredientMaster in the database
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientMaster() throws Exception {
        // Initialize the database
        ingredientMasterService.save(ingredientMaster);

        int databaseSizeBeforeDelete = ingredientMasterRepository.findAll().size();

        // Get the ingredientMaster
        restIngredientMasterMockMvc.perform(delete("/api/ingredient-masters/{id}", ingredientMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientMaster> ingredientMasterList = ingredientMasterRepository.findAll();
        assertThat(ingredientMasterList).hasSize(databaseSizeBeforeDelete - 1);
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
}
