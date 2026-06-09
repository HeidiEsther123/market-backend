package mx.tecdesoftware.market_backend.domain.service;

import mx.tecdesoftware.market_backend.domain.Product;
import mx.tecdesoftware.market_backend.domain.repository.ProductRepository;
import mx.tecdesoftware.market_backend.persistence.ProductoRepository;
import mx.tecdesoftware.market_backend.persistence.crud.ProductoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(int productId){
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId){
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public boolean delete(int productId){
        // verificacion que existe antes de borrar

        if (getProduct(productId).isPresent()){
            productRepository.delete(productId);
            return true;
        } else {
            return false;
        }
    }

}

