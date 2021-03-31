package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.esi.onlinestore.domain.enumeration.OrderStatus;

/**
 * Die Klasse beschreibt Bestellungen
 */
@Entity
@Table(name = "product_order")
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = -4300077971700988697L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "placedDate")
    private Instant placedDate;
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "code")
    private String code;
    @OneToMany(mappedBy = "order")
    @Column(name = "orderItems")
    private Set<OrderItem> orderItems = new HashSet<>();
    @JsonIgnore
    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductOrder addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        return this;
    }

    public  ProductOrder removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOrder)) {
            return false;
        }
        return id != null && id.equals(((ProductOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", placedDate=" + placedDate +
                ", status=" + status +
                ", code='" + code + '\'' +
                ", orderItems=" + orderItems +
                ", customer=" + customer +
                '}';
    }
}


