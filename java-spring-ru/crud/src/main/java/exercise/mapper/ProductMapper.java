package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ReferenceMapper.class, JsonNullableMapper.class}
)
public interface ProductMapper {

    @Mapping(target = "category", source = "categoryId")
    Product map(ProductCreateDTO productCreateDTO);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDTO map(Product product);

    void update(ProductUpdateDTO productUpdateDTO, @MappingTarget Product product);
}