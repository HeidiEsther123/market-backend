package mx.tecdesoftware.market_backend.persistence;

import mx.tecdesoftware.market_backend.domain.Purchase;
import mx.tecdesoftware.market_backend.domain.repository.PurchaseRepository;
import mx.tecdesoftware.market_backend.persistence.crud.CompraCrudRepository;
import mx.tecdesoftware.market_backend.persistence.entity.Compra;
import mx.tecdesoftware.market_backend.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        List<Compra> compras = (List<Compra>) compraCrudRepository.findAll();
        return purchaseMapper.toPurchases(compras);
    }

    @Override
    public List<Purchase> getByClientId(String clientId) {
        List<Compra> compras = compraCrudRepository.findByIdCliente(clientId);
        return purchaseMapper.toPurchases(compras);
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);

        // Crítico: cada producto debe referenciar a la compra principal antes de guardar
        if (compra.getProductos() != null) {
            compra.getProductos().forEach(producto -> producto.setCompra(compra));
        }

        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
}