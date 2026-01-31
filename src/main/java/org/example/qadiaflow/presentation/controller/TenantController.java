package org.example.qadiaflow.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.usecase.tenant.TenantService;
import org.example.qadiaflow.domain.model.TenantStatus;
import org.example.qadiaflow.presentation.dto.tenant.TenantRequest;
import org.example.qadiaflow.presentation.dto.tenant.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TenantRequest request) {
        boolean ok = tenantService.addTenant(request);
        return ResponseEntity.ok(ok);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody TenantRequest request) {
        boolean ok = tenantService.updateTenant(request);
        return ResponseEntity.ok(ok);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam TenantStatus status
    ) {
        boolean ok = tenantService.updateTenantStatus(id, status);
        return ResponseEntity.ok(ok);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean ok = tenantService.deleteTenant(id);
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        TenantResponse res = tenantService.getTenant(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TenantResponse> res = tenantService.getTenants(PageRequest.of(page, size));
        return ResponseEntity.ok(res);
    }
}
