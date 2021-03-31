package de.esi.onlinestore.service;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.interfaces.IOrderItemService;
import de.esi.onlinestore.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService implements IOrderItemService {
    Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public OrderItem save(OrderItem orderItem) {
        logger.debug("Request to save OrderItem {}", orderItem);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete OrderItem {}", id);
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        logger.debug("Request to get all Order Items.");
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> findOne(Long id) {
        logger.debug("Request to find Order Item {}", id);
        return orderItemRepository.findById(id);
    }
}
