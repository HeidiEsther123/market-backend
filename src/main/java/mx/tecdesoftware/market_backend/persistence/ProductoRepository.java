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
        // Tu compañero usa findByIdCategoriaOrderByNombreAsc
        List<Producto> productos = productoCrudRepository.findByCantidadOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return Optional.of(productMapper.toProducts(productos.get()));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId)
                .map(producto -> productMapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}