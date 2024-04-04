package orm.acme.dto;

import orm.acme.entity.Order;
import orm.acme.status.OrderStatus;

import java.time.LocalDateTime;

public class OrderDTO {
    private final Long id;
    private final Long petId;
    private final int quantity;
    private final LocalDateTime shipDate;
    private final OrderStatus status;
    private final Boolean complete;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.petId = order.getPetId();
        this.quantity = order.getQuantity();
        this.shipDate = order.getShipDate();
        this.status = order.getStatus();
        this.complete = order.getComplete();
    }

    public Long getId() {
        return id;
    }

    public Long getPetId() {
        return petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Boolean getComplete() {
        return complete;
    }
}
