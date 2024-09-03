package dev.werricsson.word_hierarchy_analyzer.service;

import dev.werricsson.word_hierarchy_analyzer.mapper.HierarchyMapper;
import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import dev.werricsson.word_hierarchy_analyzer.model.request.HierarchyRequest;
import dev.werricsson.word_hierarchy_analyzer.repository.HierarchyRepository;
import dev.werricsson.word_hierarchy_analyzer.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class HierarchyService {

    private final HierarchyRepository repository;
    private final HierarchyMapper mapper;

    public Hierarchy save(final HierarchyRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public List<Hierarchy> findAll() {
        return repository.findAll();
    }

    public Hierarchy findById(final String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                            format("Object not found. Id: %s, Type: %s ", id, Hierarchy.class.getSimpleName())
                        )
                );
    }
}
