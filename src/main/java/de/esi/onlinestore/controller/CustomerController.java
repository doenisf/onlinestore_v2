package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.CustomerRepository;


import de.esi.onlinestore.repository.ProductOrderRepository;
import de.esi.onlinestore.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller zur Verwaltung von Kunden {@link Customer}.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final String ENTITY_NAME = "Customer";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws BadRequestException, URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            throw new BadRequestException("A new customer cannot already have an ID: " + ENTITY_NAME);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId())).body(result);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        log.debug("REST request to get all customers.");
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get customer : {}", id);
        Optional<Customer> customer = customerService.findOne(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            throw new ResourceNotFoundException("Customer with Id \"" + id + "\" not found.");
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id, @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        log.debug("REST request to update Customer with id : {}", id);
        Optional<Customer> customerInDb = customerService.findOne(id);
        if (!customerInDb.isPresent()) {
            throw new ResourceNotFoundException("Customer with Id \"" + customerDetails.getId() + " \" not found.");
        }
        Customer result = customerService.save(customerDetails);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete customer with id : {}", id);
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


