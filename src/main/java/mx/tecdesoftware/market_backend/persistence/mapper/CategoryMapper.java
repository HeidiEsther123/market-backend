package mx.tecdesoftware.market_backend.persistence.mapper;

import mx.tecdesoftware.market_backend.domain.Category;
import mx.tecdesoftware.market_backend.persistence.entity.Categoria;
import org.mapstruct.InheritConfiguration; // Cambiado para igualar a tu compañero
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target= "categoryId"),
            @Mapping(source = "descripcion", target= "category"),
            @Mapping(source = "estado", target= "active"),
    })
    Category toCategory(Categoria categoria);

    @InheritConfiguration // Configuración idéntica a la de tu compañero
    @Mapping(target = "productos", ignore = true)
    Categoria toCategoria(Category category);
}
