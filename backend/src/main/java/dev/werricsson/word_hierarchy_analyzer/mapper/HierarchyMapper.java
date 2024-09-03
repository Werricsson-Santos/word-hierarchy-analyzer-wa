package dev.werricsson.word_hierarchy_analyzer.mapper;

import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import dev.werricsson.word_hierarchy_analyzer.model.request.HierarchyRequest;
import dev.werricsson.word_hierarchy_analyzer.model.response.HierarchyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface HierarchyMapper {

    @Mapping(target = "id", ignore = true)
    Hierarchy toEntity(final HierarchyRequest request);

    @Mapping(target = "id", ignore = true)
    Hierarchy toEntity(final HierarchyRequest request, @MappingTarget Hierarchy entity);

    HierarchyResponse toResponse(final Hierarchy entity);
}
