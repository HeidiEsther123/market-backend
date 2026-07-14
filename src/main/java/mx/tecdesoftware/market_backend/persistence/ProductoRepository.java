package mx.tecdesoftware.market_backend.persistence;

import mx.tecdesoftware.market_backend.domain.Product;
import mx.tecdesoftware.market_backend.domain.repository.ProductRepository;
import mx.tecdesoftware.market_backend.persistence.crud.ProductoCrudRepository;
import mx.tecdesoftware.market_backend.persistence.entity.Producto;
import mx.tecdesoftware.market_backend.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods -> productMapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId)
                .map(producto -> productMapper.toProduct(producto));
    }
    @Override
    public Product save(Product product) {
        // 1. Convertimos el dominio a la entidad
        Producto producto = productMapper.toProducto(product);

        // 2. FORZAMOS EL ID A NULL para que sea un INSERT
        producto.setIdProducto(null);

        // 3. ASEGURAMOS LA LLAVE FORÁNEA:
        // En lugar de borrar la categoría, le decimos explícitamente a la entidad
        // qué ID de categoría debe usar basándonos en el campo del dominio.
        if (product.getCategoryId() != 0) {
            // Asignamos directamente el ID a la entidad
            producto.setIdCategoria(product.getCategoryId());
        }

        // 4. LIMPIAMOS EL OBJETO CATEGORIA:
        // Al setear el objeto a null, Hibernate ignorará la relación objeto-objeto
        // pero MANTENDRÁ el valor del id_categoria que acabamos de asignar.
        producto.setCategoria(null);

        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}