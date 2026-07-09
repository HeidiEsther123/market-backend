package mx.tecdesoftware.market_backend.persistence.crud;

import mx.tecdesoftware.market_backend.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompraCrudRepository extends CrudRepository<Compra, Integer> {

    // Query Method: filtrar compras porid de cliente
    List<Compra> findByIdCliente(String idCliente);
}