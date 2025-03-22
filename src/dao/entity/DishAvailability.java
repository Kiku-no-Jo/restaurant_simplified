package dao.entity;

import dao.operations.DishAvailabilityDAO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DishAvailability {
    private Long id;
    private String dishName;
    private Double availability;

    public List<DishAvailability> getAllDishAvailable(){
        DishAvailabilityDAO dishAvailabilityDAO = new DishAvailabilityDAO();
        return dishAvailabilityDAO.findAll();
    }


}
