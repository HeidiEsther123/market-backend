package mx.tecdesoftware.market_backend.persistence.mapper;

import mx.tecdesoftware.market_backend.domain.PurchaseItem;
import mx.tecdesoftware.market_backend.persistence.entity.CompraProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {

    @Mapping(source = "id.idCompra", target = "purchaseId")
    @Mapping(source = "id.idProducto", target = "productId")
    @Mapping(source = "cantidad", target = "quantity")
    @Mapping(source = "total", target = "unitPrice")
    PurchaseItem toDomain(CompraProducto compraProducto);

    @InheritInverseConfiguration
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "id", ignore = true)
    CompraProducto toEntity(PurchaseItem purchaseItem);
}
