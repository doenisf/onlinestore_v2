package de.esi.onlinestore.service;

import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.interfaces.IProductOrderService;
import de.esi.onlinestore.repository.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService implements IProductOrderService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        logger.debug("Request to save Product Order {}", productOrder);
        return productOrderRepository.save(productOrder);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Product Order {}", id);
        productOrderRepository.deleteById(id);
    }

    @Override
    public List<ProductOrder> findAll() {
        logger.debug("Request to get all Product Order.");
        return productOrderRepository.findAll();
    }

    @Override
    public Optional<ProductOrder> findOne(Long id) {
        logger.debug("Request to find Product Order {}", id);
        return productOrderRepository.findById(id);
    }
}
