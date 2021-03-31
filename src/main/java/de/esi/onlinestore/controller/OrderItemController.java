package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;


import de.esi.onlinestore.service.OrderItemService;
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
 * REST Controller zur von Bestellpositionen {@link OrderItem}.
 */
@RestController
@RequestMapping("/api")
public class OrderItemController {

    private final Logger log = LoggerFactory.getLogger(OrderItemController.class);
    private final String ENTITY_NAME = "OrderItem";

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/order-items")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) throws BadRequestException, URISyntaxException {
        log.debug("REST request to save Order Item : {}", orderItem);
        if (orderItem.getId() != null) {
            throw new BadRequestException("A new Order Item cannot already have an ID: " + ENTITY_NAME);
        }
        OrderItem result = orderItemService.save(orderItem);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId())).body(result);
    }

    @GetMapping("/order-items")
    public List<OrderItem> getAllOrderItems() {
        log.debug("REST request to get all Order Items.");
        return orderItemService.findAll();
    }

    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Order Item : {}", id);
        Optional<OrderItem> orderItem = orderItemService.findOne(id);
        if (orderItem.isPresent()) {
            return ResponseEntity.ok(orderItem.get());
        } else {
            throw new ResourceNotFoundException("Order Item with Id \"" + id + "\" not found.");
        }
    }

    @PutMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderItem orderItemDetails) throws ResourceNotFoundException {
        log.debug("REST request to update Order Item with id : {}", id);
        Optional<OrderItem> orderItemInDb = orderItemService.findOne(id);
        if (!orderItemInDb.isPresent()) {
            throw new ResourceNotFoundException("Order Item with Id \"" + orderItemDetails.getId() + " \" not found.");
        }
        OrderItem result = orderItemService.save(orderItemDetails);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete Order Item with id : {}", id);
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
