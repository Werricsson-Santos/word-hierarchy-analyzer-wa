package dev.werricsson.word_hierarchy_analyzer.service;

import dev.werricsson.word_hierarchy_analyzer.mapper.HierarchyMapper;
import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import dev.werricsson.word_hierarchy_analyzer.model.request.HierarchyRequest;
import dev.werricsson.word_hierarchy_analyzer.repository.HierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
