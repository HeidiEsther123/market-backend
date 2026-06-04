package mx.tecdesoftware.market_backend.persistence;

import mx.tecdesoftware.market_backend.persistence.crud.ProductoCrudRepository;
import mx.tecdesoftware.market_backend.persistence.entity.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository {

    private ProductoCrudRepository productoCrudRepository;


    public List<Producto> getAll() {
        // Se "castea" Iterable a lista
        return (List<Producto>) productoCrudRepository.findAll();
    }


    public List<Producto> getByCategoria(int idCategoria) {
        // Nota: Asegúrate de usar findByIdCategoria... si cambiaste el nombre en tu interfaz
        return productoCrudRepository.findByCantidadOrderByNombreAsc(idCategoria);
    }


    public Optional<List<Producto>> getEscasos(int cantidad) {
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);
    }


    public Optional<Producto> getProductoById(int idProducto) {
        return productoCrudRepository.findById(idProducto);
    }


    public Producto addProducto(Producto producto) {
        return productoCrudRepository.save(producto);
    }


    public void deleteProductoById(int idProducto) {
        productoCrudRepository.deleteById(idProducto);
    }
}