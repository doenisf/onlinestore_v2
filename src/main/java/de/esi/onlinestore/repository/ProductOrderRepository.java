package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.domain.ProductOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
