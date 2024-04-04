package orm.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import orm.acme.status.OrderStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "`Order`")
public class Order extends PanacheEntityBase {
    @Id
    @NotNull
    @Schema(required=true,example="1")
    @Column(name = "Order_Code")
    private Long id;

    @NotNull
    @Schema(required=true,example="0")
    @Column(name = "Order_Pet_Code")
    private Long petId;

    @NotNull
    @Schema(required=true,example="0")
    @Column(name = "Order_Quantity")
    private int quantity;

    @NotNull
    @Schema(required=true,example="2014-04-28T16:00:49.493")
    @Column(name = "Order_ShipDate")
    private LocalDateTime shipDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(required=true,example="DELIVERED")
    @Column(name = "Order_Status")
    private OrderStatus status;

    @NotNull
    @Schema(required=true,example="true")
    @Column(name = "Order_Complete")
    private Boolean complete;

    public Order(){}

    public Order(Long id, Long petId, int quantity, LocalDateTime shipDate, OrderStatus status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
