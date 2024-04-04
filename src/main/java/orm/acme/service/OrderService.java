package orm.acme.service;

import jakarta.transaction.Transactional;
import orm.acme.dto.OrderDTO;
import orm.acme.entity.Order;
import orm.acme.entity.Pet;

import java.util.Optional;

public class OrderService {
    public Optional<Order> findOrderById(Long id){
        return Order.find("id", id)
                .singleResultOptional();
    }

    public Optional<Pet> findPetById(Long id){
        return Pet.find("id", id)
                .singleResultOptional();
    }

    public OrderDTO getOrderDTO(Order order){
        return new OrderDTO(order);
    }

    @Transactional
    public void saveOrder(Order order){
        Order.persist(order);
    }

    @Transactional
    public void deleteOrderById(Long id){
        Order.delete("id", id);
    }

}
