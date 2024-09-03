package dev.werricsson.word_hierarchy_analyzer.controller;

import dev.werricsson.word_hierarchy_analyzer.model.request.HierarchyRequest;
import dev.werricsson.word_hierarchy_analyzer.model.response.HierarchyResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface HierarchyController {

    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody HierarchyRequest request);

    @GetMapping
    ResponseEntity<List<HierarchyResponse>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<HierarchyResponse> findById(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseEntity<HierarchyResponse> update(@PathVariable String id, @RequestBody HierarchyRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id);

}
