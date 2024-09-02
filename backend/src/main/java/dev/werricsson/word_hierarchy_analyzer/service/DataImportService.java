package dev.werricsson.word_hierarchy_analyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class DataImportService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void importData() {
        try {
            InputStream inputStream = new ClassPathResource("dicts/hierarchy.json").getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> data = mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});

            mongoTemplate.dropCollection("hierarchies");

            mongoTemplate.insert(data, "hierarchies");

            mongoTemplate.indexOps(Hierarchy.class)
                         .ensureIndex(new Index().on("category", Sort.Direction.ASC)
                                                 .unique()
                                                 .named("_category_"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
