package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.ProductCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
