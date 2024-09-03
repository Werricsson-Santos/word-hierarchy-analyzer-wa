package dev.werricsson.word_hierarchy_analyzer.controller.impl;

import dev.werricsson.word_hierarchy_analyzer.controller.HierarchyController;
import dev.werricsson.word_hierarchy_analyzer.mapper.HierarchyMapper;
import dev.werricsson.word_hierarchy_analyzer.model.request.HierarchyRequest;
import dev.werricsson.word_hierarchy_analyzer.model.response.HierarchyResponse;
import dev.werricsson.word_hierarchy_analyzer.service.HierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hierarchies")
@RequiredArgsConstructor
public class HierarchyControllerImpl implements HierarchyController {

    private final HierarchyService service;

    private final HierarchyMapper mapper;

    @Override
    public ResponseEntity<Void> save(HierarchyRequest request) {
        service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<HierarchyResponse>> findAll() {
        List<HierarchyResponse> responses = service.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<HierarchyResponse> findById(String id) {
        Optional<HierarchyResponse> response = Optional
                .ofNullable(service.findById(id))
                .map(mapper::toResponse);

        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<HierarchyResponse> update(String id, HierarchyRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        return null;
    }
}
