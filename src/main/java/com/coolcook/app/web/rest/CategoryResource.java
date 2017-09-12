package com.coolcook.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coolcook.app.domain.Category;

import com.coolcook.app.repository.CategoryRepository;
import com.coolcook.app.web.rest.util.HeaderUtil;
import com.coolcook.app.service.dto.CategoryDTO;
import com.coolcook.app.service.mapper.CategoryMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryResource(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * POST  /categories : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryDTO, or with status 400 (Bad Request) if the category has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categories")
    @Timed
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new category cannot already have an ID")).body(null);
        }
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categories : Updates an existing category.
     *
     * @param categoryDTO the categoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryDTO,
     * or with status 400 (Bad Request) if the categoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categories")
    @Timed
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        log.debug("REST request to update Category : {}", categoryDTO);
        if (categoryDTO.getId() == null) {
            return createCategory(categoryDTO);
        }
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    @Timed
    public List<CategoryDTO> getAllCategories() {
        log.debug("REST request to get all Categories");
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }

    /**
     * GET  /categories/:id : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categories/{id}")
    @Timed
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoryDTO));
    }

    /**
     * DELETE  /categories/:id : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
