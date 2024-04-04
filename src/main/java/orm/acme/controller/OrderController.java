package orm.acme.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import orm.acme.dto.OrderDTO;
import orm.acme.entity.Order;
import orm.acme.entity.Pet;
import orm.acme.service.OrderService;
import java.util.Optional;

@Path("api/v1/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Store", description = "Access to Petstore orders")
public class OrderController {
    private final OrderService orderService = new OrderService();

    @POST
    @Transactional
    @Operation(
            operationId = "createOrder",
            summary = "Place an order for a pet",
            description = "place an order for a pet"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/store/order")
    public Response createOrder(Order order) {
        Optional<Order> findOrder = orderService.findOrderById(order.getId());
        if(findOrder.isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }
        //check if orderId between 1-10
        if(order.getId() < 1 || order.getId() > 10){
            return Response.status(Response.Status.BAD_REQUEST).entity("Number must be between 1-10").build();
        }
        //check if petID does not exist in db
        Optional<Pet> findPet = orderService.findPetById(order.getPetId());
        if(findPet.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("No This Pet ID Exist").build();
        }

        orderService.saveOrder(order);
        return Response.status(Response.Status.CREATED).entity(orderService.getOrderDTO(order)).build();
    }

    @GET
    @Operation(
            operationId = "getByOrderId",
            summary = "Find purchase order by ID",
            description = "For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/store/order/{orderId}")
    public Response getByOrderID(
            @Parameter(description = "ID of pet that needs to be fetched", required = true)
            @PathParam("orderId") long orderId){
        Optional<Order> findOrder = orderService.findOrderById(orderId);
        //check orderId must be between 1 - 10
        if(orderId < 1 || orderId > 10){
            return Response.status(Response.Status.BAD_REQUEST).entity("Number must be around 1-10").build();
        }
        else if(findOrder.isPresent()){
            OrderDTO dbOrder = orderService.getOrderDTO(findOrder.get());
            return Response.ok(dbOrder).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
    }

    @DELETE
    @Operation(
            operationId = "deleteOrder",
            summary = "Delete purchase order by ID",
            description = "For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors"
    )
    @APIResponse(
            responseCode = "204",
            description = "Order deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid ID supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/store/order/{orderId}")
    @Transactional
    public Response deleteOrderById(
            @Parameter(description = "ID of the order that needs to be deleted", required = true)
            @PathParam("orderId") long orderId){
        Optional<Order> findOrder = orderService.findOrderById(orderId);
        if(findOrder.isPresent()){
            orderService.deleteOrderById(orderId);
            return Response.status(Response.Status.NO_CONTENT).entity("Order deleted").build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
    }
}
