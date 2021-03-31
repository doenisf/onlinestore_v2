package de.esi.onlinestore.service;

import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.interfaces.IProductCategoryService;
import de.esi.onlinestore.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService implements IProductCategoryService {
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        logger.debug("Request to save Product Category {}", productCategory);
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Product Category {}", id);
        productCategoryRepository.deleteById(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        logger.debug("Request to get all Product Category.");
        return productCategoryRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> findOne(Long id) {
        logger.debug("Request to find Product Category {}", id);
        return productCategoryRepository.findById(id);
    }
}
