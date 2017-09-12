package com.coolcook.app.web.rest;

import com.coolcook.app.CoolcookApp;

import com.coolcook.app.domain.Quantity;
import com.coolcook.app.repository.QuantityRepository;
import com.coolcook.app.service.dto.QuantityDTO;
import com.coolcook.app.service.mapper.QuantityMapper;
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

import com.coolcook.app.domain.enumeration.UnitOfQuantity;
/**
 * Test class for the QuantityResource REST controller.
 *
 * @see QuantityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolcookApp.class)
public class QuantityResourceIntTest {

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final UnitOfQuantity DEFAULT_UNIT = UnitOfQuantity.KILOGRAM;
    private static final UnitOfQuantity UPDATED_UNIT = UnitOfQuantity.GRAM;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private QuantityMapper quantityMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuantityMockMvc;

    private Quantity quantity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuantityResource quantityResource = new QuantityResource(quantityRepository, quantityMapper);
        this.restQuantityMockMvc = MockMvcBuilders.standaloneSetup(quantityResource)
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
    public static Quantity createEntity(EntityManager em) {
        Quantity quantity = new Quantity()
            .qty(DEFAULT_QTY)
            .unit(DEFAULT_UNIT);
        return quantity;
    }

    @Before
    public void initTest() {
        quantity = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuantity() throws Exception {
        int databaseSizeBeforeCreate = quantityRepository.findAll().size();

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);
        restQuantityMockMvc.perform(post("/api/quantities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isCreated());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate + 1);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testQuantity.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createQuantityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quantityRepository.findAll().size();

        // Create the Quantity with an existing ID
        quantity.setId(1L);
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantityMockMvc.perform(post("/api/quantities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuantities() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get all the quantityList
        restQuantityMockMvc.perform(get("/api/quantities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantity.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get the quantity
        restQuantityMockMvc.perform(get("/api/quantities/{id}", quantity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quantity.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuantity() throws Exception {
        // Get the quantity
        restQuantityMockMvc.perform(get("/api/quantities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity
        Quantity updatedQuantity = quantityRepository.findOne(quantity.getId());
        updatedQuantity
            .qty(UPDATED_QTY)
            .unit(UPDATED_UNIT);
        QuantityDTO quantityDTO = quantityMapper.toDto(updatedQuantity);

        restQuantityMockMvc.perform(put("/api/quantities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testQuantity.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuantityMockMvc.perform(put("/api/quantities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isCreated());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);
        int databaseSizeBeforeDelete = quantityRepository.findAll().size();

        // Get the quantity
        restQuantityMockMvc.perform(delete("/api/quantities/{id}", quantity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quantity.class);
        Quantity quantity1 = new Quantity();
        quantity1.setId(1L);
        Quantity quantity2 = new Quantity();
        quantity2.setId(quantity1.getId());
        assertThat(quantity1).isEqualTo(quantity2);
        quantity2.setId(2L);
        assertThat(quantity1).isNotEqualTo(quantity2);
        quantity1.setId(null);
        assertThat(quantity1).isNotEqualTo(quantity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuantityDTO.class);
        QuantityDTO quantityDTO1 = new QuantityDTO();
        quantityDTO1.setId(1L);
        QuantityDTO quantityDTO2 = new QuantityDTO();
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
        quantityDTO2.setId(quantityDTO1.getId());
        assertThat(quantityDTO1).isEqualTo(quantityDTO2);
        quantityDTO2.setId(2L);
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
        quantityDTO1.setId(null);
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quantityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quantityMapper.fromId(null)).isNull();
    }
}
