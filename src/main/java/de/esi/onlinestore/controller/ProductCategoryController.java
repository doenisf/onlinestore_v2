package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;


import de.esi.onlinestore.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller zur Verwaltung von Produktkategorien {@link ProductCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryController {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryController.class);
    private final String ENTITY_NAME = "ProductCategory";

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/product-categories")
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) throws BadRequestException, URISyntaxException {
        log.debug("REST request to save Product Category : {}", productCategory);
        if (productCategory.getId() != null) {
            throw new BadRequestException("A new Product Category cannot already have an ID: " + ENTITY_NAME);
        }
        ProductCategory result = productCategoryService.save(productCategory);
        return ResponseEntity.created(new URI("/api/product-categories/" + result.getId())).body(result);
    }

    @GetMapping("/product-categories")
    public List<ProductCategory> getAllProductCategories() {
        log.debug("REST request to get all Product Categories.");
        return productCategoryService.findAll();
    }

    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Product Category with id : {}", id);
        Optional<ProductCategory> productCategory = productCategoryService.findOne(id);
        if (productCategory.isPresent()) {
            return ResponseEntity.ok(productCategory.get());
        } else {
            throw new ResourceNotFoundException("Product Category with ID \"" + id + "\" not found.");
        }
    }

    @PutMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody ProductCategory productCategoryDetails) throws ResourceNotFoundException {
        log.debug("REST request to update Product Category with id : " + id);
        Optional<ProductCategory> productCategoryInDb = productCategoryService.findOne(id);
        if (!productCategoryInDb.isPresent()) {
            throw new ResourceNotFoundException("Product Category with Id \"" + productCategoryDetails.getId() + "\" not found.");
        }
        ProductCategory result = productCategoryService.save(productCategoryDetails);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/product-categories/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete Product Category with id : {}", id);
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
