package de.esi.onlinestore;

import de.esi.onlinestore.domain.*;
import de.esi.onlinestore.domain.enumeration.*;
import de.esi.onlinestore.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OnlineStoreApp implements CommandLineRunner {

	@Autowired
	private ProductOrderRepository productOrderRepo;
	@Autowired
	private OrderItemRepository orderItemRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ProductRepository productRepo;







	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer();
		customer.setFirstName("Jon");
		customer.setLastName("Mueller");
		customer.setEmail("mail@mail.de");
		customer = customerRepo.save(customer);


		Product iPhone = new Product();
		iPhone.setName("Iphone X");
		iPhone.setPrice(10F);
		iPhone.setDescription("String String String");
		iPhone.setSize(Size.S);
		iPhone = productRepo.save(iPhone);

		Product notebook = new Product();
		notebook.setName("Surface Book");
		notebook.setPrice(100F);
		notebook.setDescription("String String String");
		iPhone.setSize(Size.S);
		notebook = productRepo.save(iPhone);
        System.out.println("Product Notebook");



		ProductOrder productOrder = new ProductOrder();
		productOrder.setStatus(OrderStatus.PENDING);
		productOrder.setCustomer(customer);
		productOrder = productOrderRepo.save(productOrder);

		OrderItem orderItemIphone = new OrderItem();
		orderItemIphone.setQuantity(10);
		orderItemIphone.setProduct(iPhone);
		orderItemIphone.setTotalPrice(iPhone.getPrice()*10);
		orderItemIphone.setStatus(OrderItemStatus.AVAILABLE);
		orderItemIphone.setOrder(productOrder);

		orderItemIphone = orderItemRepo.save(orderItemIphone);



		OrderItem orderItemNotebook = new OrderItem();
		orderItemNotebook.setQuantity(100);
		orderItemNotebook.setProduct(notebook);
		orderItemNotebook.setStatus(OrderItemStatus.AVAILABLE);
		orderItemNotebook.setTotalPrice(notebook.getPrice()*100);
		orderItemNotebook.setOrder(productOrder);



		orderItemNotebook = orderItemRepo.save(orderItemNotebook);
		System.out.println("orderItemIphone");


		productOrder.addOrderItem(orderItemIphone);
		productOrder.addOrderItem(orderItemNotebook);
		productOrder =productOrderRepo.save(productOrder);

		System.out.println("Product Order update: ");











	}

}

