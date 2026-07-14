package mx.tecdesoftware.market_backend.persistence;

import mx.tecdesoftware.market_backend.domain.Purchase;
import mx.tecdesoftware.market_backend.domain.repository.PurchaseRepository;
import mx.tecdesoftware.market_backend.persistence.crud.CompraCrudRepository;
import mx.tecdesoftware.market_backend.persistence.entity.Compra;
import mx.tecdesoftware.market_backend.persistence.entity.CompraProducto;
import mx.tecdesoftware.market_backend.persistence.entity.CompraProductoPK;
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
        // 1. Convertimos el dominio de la compra a la entidad principal
        Compra compra = purchaseMapper.toCompra(purchase);
        compra.setIdCompra(null); // Asegura que se genere un nuevo ID secuencial

        // 2. Sincronizamos manualmente la lista mapeada con los ID correctos
        if (purchase.getItems() != null && compra.getProductos() != null) {
            for (int i = 0; i < purchase.getItems().size(); i++) {
                var domainItem = purchase.getItems().get(i);
                CompraProducto entityProduct = compra.getProductos().get(i);

                // Enlazamos bidireccionalmente el objeto padre
                entityProduct.setCompra(compra);

                // Instanciamos explícitamente la llave compuesta para evitar el Null en Postgres
                CompraProductoPK pk = new CompraProductoPK();
                pk.setIdCompra(null); // Hibernate lo rellenará tras el insert de Compra
                pk.setIdProducto(domainItem.getProductId()); // Inyectamos el ID directo del dominio plano

                entityProduct.setId(pk);

                // Forzamos que el estado nunca sea nulo para cumplir la restricción booleana
                if (entityProduct.getEstado() == null) {
                    entityProduct.setEstado(true);
                }
            }
        }

        // 3. Guardamos en cascada de manera segura
        Compra compraGuardada = compraCrudRepository.save(compra);
        return purchaseMapper.toPurchase(compraGuardada);
    }
}