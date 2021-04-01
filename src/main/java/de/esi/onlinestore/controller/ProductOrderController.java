package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;

import de.esi.onlinestore.service.ProductOrderService;
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
 * REST Controller zur Handhabung von Bestellungen {@link ProductOrder}.
 */
@RestController
@RequestMapping("/api")
public class ProductOrderController {

    private final Logger log = LoggerFactory.getLogger(ProductOrderController.class);
    private final String ENTITY_NAME = "Order";

    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<ProductOrder> createProductOrder(@RequestBody ProductOrder productOrder) throws BadRequestException, URISyntaxException {
        log.debug("REST request to save Product Order : {}", productOrder);
        if (productOrder.getId() != null) {
            throw new BadRequestException("A new Product Order cannot already have an ID: " + ENTITY_NAME);
        }
        ProductOrder result = productOrderService.save(productOrder);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ProductOrder>> getAllProductOrders() {
        log.debug("REST request to get all Product Orders.");
        return ResponseEntity.ok(productOrderService.findAll());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Product Order : {}", id);
        Optional<ProductOrder> productOrder = productOrderService.findOne(id);
        if (productOrder.isPresent()) {
            return ResponseEntity.ok(productOrder.get());
        } else {
            throw new ResourceNotFoundException("Product Order with Id \"" + id + "\" not found.");
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductOrder productOrderDetials) throws ResourceNotFoundException {
        log.debug("REST request to update Product Order with id : {}", id);
        Optional<ProductOrder> productOrderInDb = productOrderService.findOne(id);
        if (!productOrderInDb.isPresent()) {
            throw new ResourceNotFoundException("Product Order with Id \"" + productOrderDetials.getId() + " \" not found.");
        }
        ProductOrder result = productOrderService.save(productOrderDetials);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id) {
        log.debug("REST request to delete Product Order with id : {}", id);
        productOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


