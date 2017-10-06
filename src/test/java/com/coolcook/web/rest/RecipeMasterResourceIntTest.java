package com.coolcook.web.rest;

import com.coolcook.CoolcookApp;

import com.coolcook.domain.RecipeMaster;
import com.coolcook.repository.RecipeMasterRepository;
import com.coolcook.service.RecipeMasterService;
import com.coolcook.repository.search.RecipeMasterSearchRepository;
import com.coolcook.service.dto.RecipeMasterDTO;
import com.coolcook.service.mapper.RecipeMasterMapper;
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

/**
 * Test class for the RecipeMasterResource REST controller.
 *
 * @see RecipeMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class RecipeMasterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION_IN_MIN = 1;
    private static final Integer UPDATED_DURATION_IN_MIN = 2;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ORIGINAL = false;
    private static final Boolean UPDATED_IS_ORIGINAL = true;

    private static final Integer DEFAULT_ORIGINAL_RECIPE_ID = 1;
    private static final Integer UPDATED_ORIGINAL_RECIPE_ID = 2;

    private static final Boolean DEFAULT_IS_VEG = false;
    private static final Boolean UPDATED_IS_VEG = true;

    @Autowired
    private RecipeMasterRepository recipeMasterRepository;

    @Autowired
    private RecipeMasterMapper recipeMasterMapper;

    @Autowired
    private RecipeMasterService recipeMasterService;

    @Autowired
    private RecipeMasterSearchRepository recipeMasterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipeMasterMockMvc;

    private RecipeMaster recipeMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RecipeMasterResource recipeMasterResource = new RecipeMasterResource(recipeMasterService);
        this.restRecipeMasterMockMvc = MockMvcBuilders.standaloneSetup(recipeMasterResource)
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
    public static RecipeMaster createEntity(EntityManager em) {
        RecipeMaster recipeMaster = new RecipeMaster()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .process(DEFAULT_PROCESS)
            .durationInMin(DEFAULT_DURATION_IN_MIN)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isOriginal(DEFAULT_IS_ORIGINAL)
            .originalRecipeId(DEFAULT_ORIGINAL_RECIPE_ID)
            .isVeg(DEFAULT_IS_VEG);
        return recipeMaster;
    }

    @Before
    public void initTest() {
        recipeMasterSearchRepository.deleteAll();
        recipeMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeMaster() throws Exception {
        int databaseSizeBeforeCreate = recipeMasterRepository.findAll().size();

        // Create the RecipeMaster
        RecipeMasterDTO recipeMasterDTO = recipeMasterMapper.toDto(recipeMaster);
        restRecipeMasterMockMvc.perform(post("/api/recipe-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeMaster in the database
        List<RecipeMaster> recipeMasterList = recipeMasterRepository.findAll();
        assertThat(recipeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeMaster testRecipeMaster = recipeMasterList.get(recipeMasterList.size() - 1);
        assertThat(testRecipeMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecipeMaster.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testRecipeMaster.getProcess()).isEqualTo(DEFAULT_PROCESS);
        assertThat(testRecipeMaster.getDurationInMin()).isEqualTo(DEFAULT_DURATION_IN_MIN);
        assertThat(testRecipeMaster.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testRecipeMaster.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testRecipeMaster.isIsOriginal()).isEqualTo(DEFAULT_IS_ORIGINAL);
        assertThat(testRecipeMaster.getOriginalRecipeId()).isEqualTo(DEFAULT_ORIGINAL_RECIPE_ID);
        assertThat(testRecipeMaster.isIsVeg()).isEqualTo(DEFAULT_IS_VEG);

        // Validate the RecipeMaster in Elasticsearch
        RecipeMaster recipeMasterEs = recipeMasterSearchRepository.findOne(testRecipeMaster.getId());
        assertThat(recipeMasterEs).isEqualToComparingFieldByField(testRecipeMaster);
    }

    @Test
    @Transactional
    public void createRecipeMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeMasterRepository.findAll().size();

        // Create the RecipeMaster with an existing ID
        recipeMaster.setId(1L);
        RecipeMasterDTO recipeMasterDTO = recipeMasterMapper.toDto(recipeMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMasterMockMvc.perform(post("/api/recipe-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RecipeMaster> recipeMasterList = recipeMasterRepository.findAll();
        assertThat(recipeMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipeMasters() throws Exception {
        // Initialize the database
        recipeMasterRepository.saveAndFlush(recipeMaster);

        // Get all the recipeMasterList
        restRecipeMasterMockMvc.perform(get("/api/recipe-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS.toString())))
            .andExpect(jsonPath("$.[*].durationInMin").value(hasItem(DEFAULT_DURATION_IN_MIN)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isOriginal").value(hasItem(DEFAULT_IS_ORIGINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].originalRecipeId").value(hasItem(DEFAULT_ORIGINAL_RECIPE_ID)))
            .andExpect(jsonPath("$.[*].isVeg").value(hasItem(DEFAULT_IS_VEG.booleanValue())));
    }

    @Test
    @Transactional
    public void getRecipeMaster() throws Exception {
        // Initialize the database
        recipeMasterRepository.saveAndFlush(recipeMaster);

        // Get the recipeMaster
        restRecipeMasterMockMvc.perform(get("/api/recipe-masters/{id}", recipeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipeMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.process").value(DEFAULT_PROCESS.toString()))
            .andExpect(jsonPath("$.durationInMin").value(DEFAULT_DURATION_IN_MIN))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isOriginal").value(DEFAULT_IS_ORIGINAL.booleanValue()))
            .andExpect(jsonPath("$.originalRecipeId").value(DEFAULT_ORIGINAL_RECIPE_ID))
            .andExpect(jsonPath("$.isVeg").value(DEFAULT_IS_VEG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeMaster() throws Exception {
        // Get the recipeMaster
        restRecipeMasterMockMvc.perform(get("/api/recipe-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeMaster() throws Exception {
        // Initialize the database
        recipeMasterRepository.saveAndFlush(recipeMaster);
        recipeMasterSearchRepository.save(recipeMaster);
        int databaseSizeBeforeUpdate = recipeMasterRepository.findAll().size();

        // Update the recipeMaster
        RecipeMaster updatedRecipeMaster = recipeMasterRepository.findOne(recipeMaster.getId());
        updatedRecipeMaster
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .process(UPDATED_PROCESS)
            .durationInMin(UPDATED_DURATION_IN_MIN)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isOriginal(UPDATED_IS_ORIGINAL)
            .originalRecipeId(UPDATED_ORIGINAL_RECIPE_ID)
            .isVeg(UPDATED_IS_VEG);
        RecipeMasterDTO recipeMasterDTO = recipeMasterMapper.toDto(updatedRecipeMaster);

        restRecipeMasterMockMvc.perform(put("/api/recipe-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeMasterDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeMaster in the database
        List<RecipeMaster> recipeMasterList = recipeMasterRepository.findAll();
        assertThat(recipeMasterList).hasSize(databaseSizeBeforeUpdate);
        RecipeMaster testRecipeMaster = recipeMasterList.get(recipeMasterList.size() - 1);
        assertThat(testRecipeMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecipeMaster.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testRecipeMaster.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testRecipeMaster.getDurationInMin()).isEqualTo(UPDATED_DURATION_IN_MIN);
        assertThat(testRecipeMaster.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testRecipeMaster.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testRecipeMaster.isIsOriginal()).isEqualTo(UPDATED_IS_ORIGINAL);
        assertThat(testRecipeMaster.getOriginalRecipeId()).isEqualTo(UPDATED_ORIGINAL_RECIPE_ID);
        assertThat(testRecipeMaster.isIsVeg()).isEqualTo(UPDATED_IS_VEG);

        // Validate the RecipeMaster in Elasticsearch
        RecipeMaster recipeMasterEs = recipeMasterSearchRepository.findOne(testRecipeMaster.getId());
        assertThat(recipeMasterEs).isEqualToComparingFieldByField(testRecipeMaster);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeMaster() throws Exception {
        int databaseSizeBeforeUpdate = recipeMasterRepository.findAll().size();

        // Create the RecipeMaster
        RecipeMasterDTO recipeMasterDTO = recipeMasterMapper.toDto(recipeMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeMasterMockMvc.perform(put("/api/recipe-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeMaster in the database
        List<RecipeMaster> recipeMasterList = recipeMasterRepository.findAll();
        assertThat(recipeMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecipeMaster() throws Exception {
        // Initialize the database
        recipeMasterRepository.saveAndFlush(recipeMaster);
        recipeMasterSearchRepository.save(recipeMaster);
        int databaseSizeBeforeDelete = recipeMasterRepository.findAll().size();

        // Get the recipeMaster
        restRecipeMasterMockMvc.perform(delete("/api/recipe-masters/{id}", recipeMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean recipeMasterExistsInEs = recipeMasterSearchRepository.exists(recipeMaster.getId());
        assertThat(recipeMasterExistsInEs).isFalse();

        // Validate the database is empty
        List<RecipeMaster> recipeMasterList = recipeMasterRepository.findAll();
        assertThat(recipeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRecipeMaster() throws Exception {
        // Initialize the database
        recipeMasterRepository.saveAndFlush(recipeMaster);
        recipeMasterSearchRepository.save(recipeMaster);

        // Search the recipeMaster
        restRecipeMasterMockMvc.perform(get("/api/_search/recipe-masters?query=id:" + recipeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS.toString())))
            .andExpect(jsonPath("$.[*].durationInMin").value(hasItem(DEFAULT_DURATION_IN_MIN)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isOriginal").value(hasItem(DEFAULT_IS_ORIGINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].originalRecipeId").value(hasItem(DEFAULT_ORIGINAL_RECIPE_ID)))
            .andExpect(jsonPath("$.[*].isVeg").value(hasItem(DEFAULT_IS_VEG.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeMaster.class);
        RecipeMaster recipeMaster1 = new RecipeMaster();
        recipeMaster1.setId(1L);
        RecipeMaster recipeMaster2 = new RecipeMaster();
        recipeMaster2.setId(recipeMaster1.getId());
        assertThat(recipeMaster1).isEqualTo(recipeMaster2);
        recipeMaster2.setId(2L);
        assertThat(recipeMaster1).isNotEqualTo(recipeMaster2);
        recipeMaster1.setId(null);
        assertThat(recipeMaster1).isNotEqualTo(recipeMaster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeMasterDTO.class);
        RecipeMasterDTO recipeMasterDTO1 = new RecipeMasterDTO();
        recipeMasterDTO1.setId(1L);
        RecipeMasterDTO recipeMasterDTO2 = new RecipeMasterDTO();
        assertThat(recipeMasterDTO1).isNotEqualTo(recipeMasterDTO2);
        recipeMasterDTO2.setId(recipeMasterDTO1.getId());
        assertThat(recipeMasterDTO1).isEqualTo(recipeMasterDTO2);
        recipeMasterDTO2.setId(2L);
        assertThat(recipeMasterDTO1).isNotEqualTo(recipeMasterDTO2);
        recipeMasterDTO1.setId(null);
        assertThat(recipeMasterDTO1).isNotEqualTo(recipeMasterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipeMasterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipeMasterMapper.fromId(null)).isNull();
    }
}
