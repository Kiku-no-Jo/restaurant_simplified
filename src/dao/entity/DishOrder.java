package dao.entity;

import dao.operations.DishAvailabilityDAO;
import dao.operations.DishOrderDAO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DishOrder {
    private Long id;
    private String dishName;
    private Double quantityToOrder;
    private Double unitPrice;
    private Order order;

    public DishOrder(long id, String dishName, double quantityToOrder, double unitPrice, Order order) {
        this.id = id;
        this.dishName = dishName;
        this.quantityToOrder = quantityToOrder;
        this.unitPrice = unitPrice;
        this.order = order;
    }

    public List<DishOrder> getAllDishOrder(){
        DishOrderDAO dishOrderDAO = new DishOrderDAO();
        return dishOrderDAO.findAll();
    }

    public Double getTotalPrice(){
        return quantityToOrder*unitPrice;
    }
}
