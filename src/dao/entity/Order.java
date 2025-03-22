package dao.entity;

import dao.operations.OrderDAO;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dao.entity.OrderPaymentStatus.PAID;
import static dao.entity.OrderPaymentStatus.UNPAID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    private Long id;
    private TableNumber tableNumber;
    private double amountPaid;
    private double amountDue;
    private LocalDate customerArrivalDateTime;
    private List<DishOrder> dishOrderList = new ArrayList<>();

    public List<Order> getAllOrders() {
        OrderDAO orderDAO = new OrderDAO();
        return orderDAO.findAll();
    }


    public List<DishOrder> addDishOrder(List<DishOrder> dishOrders) {
        List<DishOrder> addedDishes = new ArrayList<>();
        DishAvailability dishAvailability = new DishAvailability();
        List<DishAvailability> dishAvailabilities = dishAvailability.getAllDishAvailable();

        // Map dish availability by dish name for quick lookup
        Map<String, Double> availabilityMap = dishAvailabilities.stream()
                .collect(Collectors.toMap(DishAvailability::getDishName, DishAvailability::getAvailability));

        for (DishOrder dishOrder : dishOrders) {
            String dishName = dishOrder.getDishName();
            Double quantityToOrder = dishOrder.getQuantityToOrder();

            if (availabilityMap.containsKey(dishName) && availabilityMap.get(dishName) >= quantityToOrder) {
                // Deduct the ordered quantity from the available stock
                availabilityMap.put(dishName, availabilityMap.get(dishName) - quantityToOrder);

                // Add dish to order list
                dishOrder.setOrder(this); // Link dishOrder to the current Order instance
                this.dishOrderList.add(dishOrder);
                addedDishes.add(dishOrder);
            }
        }
        return addedDishes; // Return successfully added dish orders
    }


    public OrderPaymentStatus getPaymentStatus() {
        Order order = this;

        double difference = order.getAmountPaid() - order.getAmountDue();
        return (difference >= 0) ? PAID : UNPAID;
    }

    public Double getTotalPrice() {
        double totalAmount = 0.0;
        for (DishOrder dishOrder : dishOrderList) {
            totalAmount += dishOrder.getTotalPrice();
        }
        return totalAmount;
    }

    public double paymentProcess() {
        double result = 0.0;

        if (this.getPaymentStatus() == PAID) {
            result = this.getAmountPaid() - this.getAmountDue();
            System.out.println("Here's the monnaie: " + result);
        } else if (this.getPaymentStatus() == UNPAID) {
            result = this.getAmountDue() - this.getAmountPaid();
            System.out.println("Here's the leftToPay: " + result);
        } else {
            throw new IllegalStateException("Invalid payment status: " + this.getPaymentStatus());
        }

        return result;
    }

}
