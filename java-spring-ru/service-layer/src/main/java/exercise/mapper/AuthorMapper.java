package exercise.mapper;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.model.Author;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AuthorMapper {

    // Реализуйте маппер для конвертации DTO в сущность автора и обратно.
    // BEGIN
    public abstract Author map(AuthorCreateDTO authorCreateDTO);

    public abstract AuthorDTO map(Author author);
    // END

    public abstract void update(AuthorUpdateDTO dto, @MappingTarget Author model);
}