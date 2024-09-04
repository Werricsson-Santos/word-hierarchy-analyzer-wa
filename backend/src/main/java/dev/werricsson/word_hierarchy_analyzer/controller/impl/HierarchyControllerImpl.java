package dev.werricsson.word_hierarchy_analyzer.controller.impl;

import dev.werricsson.word_hierarchy_analyzer.controller.contract.HierarchyController;
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

        return ResponseEntity.ok(mapper.toResponse(service.findById(id)));

    }

    @Override
    public ResponseEntity<HierarchyResponse> update(String id, HierarchyRequest request) {

        return ResponseEntity.ok(mapper.toResponse(service.update(id, request)));

    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
