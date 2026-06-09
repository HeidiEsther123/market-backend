package mx.tecdesoftware.market_backend.web.controller;

import mx.tecdesoftware.market_backend.domain.repository.ProductRepository;
import mx.tecdesoftware.market_backend.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


}
