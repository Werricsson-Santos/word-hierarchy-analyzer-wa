package dev.werricsson.word_hierarchy_analyzer.mapper;

import dev.werricsson.word_hierarchy_analyzer.model.request.WordAnalysisRequest;
import dev.werricsson.word_hierarchy_analyzer.model.response.WordAnalysisResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface WordAnalysisMapper {

    @Mapping(target = "depth", source = "depth", defaultValue = "1")
    WordAnalysisResponse toResponse(WordAnalysisRequest request);

}
