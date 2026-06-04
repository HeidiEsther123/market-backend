package mx.tecdesoftware.market_backend.persistence.crud;

import mx.tecdesoftware.market_backend.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    //Obtener la lista de productos filtrados por
    //id de categoría y ordenados ascendentemente por nombre
    /*
        SELECT *
        FROM Categorias
        WHERE id_categoria = ?
        ORDER BY Nombre ASC
     */
    List<Producto> findByCantidadOrderByNombreAsc(int idCategoria);

    //Obtener los productos
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidad, boolean estado);
}