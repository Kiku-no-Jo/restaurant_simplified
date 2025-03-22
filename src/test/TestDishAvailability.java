package test;

import dao.entity.DishAvailability;
import dao.entity.DishOrder;
import dao.entity.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDishAvailability {
    private DishAvailability dish = new DishAvailability();
    private DishOrder dishOrder = new DishOrder();
    private Order order = new Order();

    @Test
    public void testDishAvailabilityGetAll_OK() {
        List<DishAvailability> dishes = dish.getAllDishAvailable();


        System.out.println(dishes);
    }

    @Test
    public void testDishOrderGetAll_OK() {
        List<DishOrder> dishOrders = dishOrder.getAllDishOrder();


        System.out.println(dishOrders);
    }

    @Test
    public void testOrderGetAll_OK() {
        List<Order> orders = order.getAllOrders();


        System.out.println(orders);
    }
}
