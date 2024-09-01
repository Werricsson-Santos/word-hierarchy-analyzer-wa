package dev.werricsson.word_hierarchy_analyzer.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@Document(collection = "hierarchies")
public class Hierarchy {

    @Id
    private String id;

    @Indexed(unique = true)
    private String category;
    private Map<String, Object> subcategories;

}
