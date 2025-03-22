package dao.operations;

import java.util.List;

public interface CRUDOperations <E> {
    List<E> findAll();

    E findById (Long id);

    List<E> saveAll(List<E> entities);
}
