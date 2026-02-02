package org.example.qadiaflow.application.usecase.tenant.impl;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.port.out.TenantPort;
import org.example.qadiaflow.application.usecase.tenant.TenantService;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.TenantStatus;
import org.example.qadiaflow.infrastructure.security.SecurityUtils;
import org.example.qadiaflow.presentation.dto.tenant.TenantRequest;
import org.example.qadiaflow.presentation.dto.tenant.TenantResponse;
import org.example.qadiaflow.presentation.mapper.TenantMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantPort port;
    private final TenantMapper mapper;

    @Override
    public boolean addTenant(TenantRequest request) {
        Tenant tenant = mapper.toEntity(request);
        tenant.setStatus(TenantStatus.SUSPENDED);

        String actor = SecurityUtils.currentUserEmail();
        tenant.setCreatedBy(actor);
        tenant.setUpdatedBy(actor);

        return port.addTenant(tenant);
    }

    @Override
    public boolean updateTenant(TenantRequest request) {
        Tenant tenant = mapper.toEntity(request);

        String actor = SecurityUtils.currentUserEmail();
        tenant.setUpdatedBy(actor);

        return port.updateTenant(tenant);
    }

    @Override
    public boolean updateTenantStatus(Long id, TenantStatus status) {
        Tenant tenant = port.getRequired(id);
        tenant.setStatus(status);

        String actor = SecurityUtils.currentUserEmail();
        tenant.setUpdatedBy(actor);

        return port.updateTenantStatus(tenant);
    }

    @Override
    public boolean deleteTenant(Long tenantId) {
        return port.deleteTenant(tenantId);
    }

    @Override
    public TenantResponse getTenant(Long tenantId) {
        Tenant tenant = port.getRequired(tenantId);
        return mapper.toResponse(tenant);
    }

    @Override
    public Page<TenantResponse> getTenants(PageRequest pageRequest) {
        return port.getTenants(pageRequest).map(mapper::toResponse);
    }
}
