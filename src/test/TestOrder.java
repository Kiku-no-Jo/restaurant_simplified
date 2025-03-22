package test;

import dao.entity.*;
import dao.operations.OrderDAO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrder {
    private Order order = new Order();
    private DishOrder dishOrder = new DishOrder();
    private OrderDAO orderDAO = new OrderDAO();

    @Test
    public void testGetDishOrderList() {
        List<DishOrder> dishOrderList = dishOrder.getAllDishOrder();
        List<DishOrder> dishOrderListOfOrder = order.addDishOrder( dishOrderList);
        System.out.println(dishOrderListOfOrder);
    }

    @Test
    public void testPayementStatus() {
        OrderDAO orderDAO = new OrderDAO();
        Order targetOrder = orderDAO.findById(6L);
        OrderPaymentStatus orderPaymentStatus = order.getPaymentStatus();
        System.out.println(orderPaymentStatus);
    }

    @Test
    void testAddDishOrder_whenStockIsEnough_shouldAddDishOrder() {
        // Fetch available dishes from database
        DishAvailability dishAvailability = new DishAvailability();
        List<DishAvailability> dishAvailabilities = dishAvailability.getAllDishAvailable();

        // Create sample dish orders
        List<DishOrder> dishOrders = Arrays.asList(
                new DishOrder(1L, "Spaghetti Carbonara", 5.0, 120.0, order),
                new DishOrder(2L, "Margherita Pizza", 2.0, 80.0,order),
                new DishOrder(3L, "Grilled Salmon", 3.0, 150.0,order)
        );


        // Call method
        List<DishOrder> addedDishes = order.addDishOrder(dishOrders);

        // Verify results
        assertEquals(3, addedDishes.size(), "All 3 dishes should be added.");
        assertTrue(order.getDishOrderList().containsAll(addedDishes), "Order should contain the added dishes.");

    }

    @Test
    void testAddDishOrder_whenStockIsNotEnough_shouldNotAddDishOrder() {
        // Fetch available dishes from database
        DishAvailability dishAvailability = new DishAvailability();
        List<DishAvailability> dishAvailabilities = dishAvailability.getAllDishAvailable();

        // Create dish order exceeding stock
        List<DishOrder> dishOrders = Arrays.asList(
                new DishOrder(1L, "Tiramisu", 30.0, 5.0, order), // Exceeds available 25
                new DishOrder(2L, "Beef Burger", 40.0, 12.0, order) // Exceeds available 35
        );

        // Call method
        List<DishOrder> addedDishes = order.addDishOrder(dishOrders);

        // Verify results
        assertEquals(0, addedDishes.size(), "No dishes should be added.");
    }

    @Test
    void testSaveAllOrders() {
        // Creating a new order
        Order newOrder = new Order();
        newOrder.setId(100L);  // Ensure this ID doesn't already exist
        newOrder.setTableNumber(TableNumber.TABLE_3);
        newOrder.setAmountPaid(20.0);
        newOrder.setAmountDue(50.0);
        newOrder.setCustomerArrivalDateTime(LocalDate.now());

        // Saving the order
        List<Order> savedOrders = orderDAO.saveAll(List.of(newOrder));
        System.out.println(savedOrders);

        // Assertions
        /*assertNotNull(savedOrders, "Saved order list should not be null");
        assertFalse(savedOrders.isEmpty(), "Saved order list should not be empty");

        Order savedOrder = savedOrders.get(0);
        assertEquals(100L, savedOrder.getId(), "Order ID should match");
        assertEquals(TableNumber.TABLE_3, savedOrder.getTableNumber(), "Table number should match");
        assertEquals(20.0, savedOrder.getAmountPaid(), "Amount paid should match");
        assertEquals(50.0, savedOrder.getAmountDue(), "Amount due should match");*/
    }

    @Test
    void payementProcessTest(){
        OrderDAO orderDAO = new OrderDAO();
        Order targetOrder = orderDAO.findById(1L);
        double result = targetOrder.paymentProcess();
        System.out.println(result);
    }
}
