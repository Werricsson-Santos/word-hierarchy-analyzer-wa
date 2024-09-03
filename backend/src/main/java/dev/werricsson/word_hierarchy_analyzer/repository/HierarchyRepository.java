package dev.werricsson.word_hierarchy_analyzer.repository;

import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HierarchyRepository {

    private final MongoTemplate mongoTemplate;

    public Hierarchy save(final Hierarchy hierarchy) {
        return mongoTemplate.save(hierarchy);
    }

    public List<Hierarchy> findAll() {
        return mongoTemplate.findAll(Hierarchy.class);
    }

    public Optional<Hierarchy> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Hierarchy.class));
    }
}
