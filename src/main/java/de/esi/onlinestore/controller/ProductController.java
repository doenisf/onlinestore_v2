package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;


import de.esi.onlinestore.service.ProductService;
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
 * REST Controller zur Verwaltung von Produkten {@link Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final String ENTITY_NAME = "Product";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws BadRequestException, URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            throw new BadRequestException("A new product cannot already have an ID: " + ENTITY_NAME);
        }
        Product result = productService.save(product);
        return ResponseEntity.created(new URI("/api/products/" + result.getId())).body(result);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        log.debug("REST request to get all products.");
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get product with id : {}", id);
        Optional<Product> product = productService.findOne(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            throw new ResourceNotFoundException("Product with ID \"" + id + "\" not found.");
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id, @Valid @RequestBody Product productDetails) throws ResourceNotFoundException {
        log.debug("REST request to update product with id : " + id);
        Optional<Product> productInDb = productService.findOne(id);
        if (!productInDb.isPresent()) {
            throw new ResourceNotFoundException("Product with Id \"" + productDetails.getId() + "\" not found.");
        }
        Product result = productService.save(productDetails);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete product with id : {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


