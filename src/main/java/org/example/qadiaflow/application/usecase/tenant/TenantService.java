package org.example.qadiaflow.application.usecase.tenant;


import org.example.qadiaflow.domain.model.TenantStatus;
import org.example.qadiaflow.presentation.dto.tenant.TenantRequest;
import org.example.qadiaflow.presentation.dto.tenant.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TenantService {

    public boolean addTenant(TenantRequest request);

    public boolean updateTenant(TenantRequest request);

    public boolean updateTenantStatus(Long id ,TenantStatus status);

    public boolean deleteTenant(Long tenantId);

    public TenantResponse getTenant(Long tenantId);

    public Page<TenantResponse> getTenants(PageRequest pageRequest);
}
