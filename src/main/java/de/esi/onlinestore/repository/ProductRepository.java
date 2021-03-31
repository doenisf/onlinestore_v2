package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.Product;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
