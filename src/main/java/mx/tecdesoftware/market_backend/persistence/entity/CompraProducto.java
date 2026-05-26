package mx.tecdesoftware.market_backend.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "compras_productos")

public class CompraProducto {
    @EmbeddedId
    private CompraProductoPK id;

    private Integer cantidad;
    private Double total;
    private Double estado;
}


