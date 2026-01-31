package org.example.qadiaflow.application.port.out;

import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.TenantStatus;
import org.example.qadiaflow.presentation.dto.tenant.TenantRequest;
import org.example.qadiaflow.presentation.dto.tenant.TenantUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TenantPort {
    Tenant getRequired(Long tenantId);

     boolean addTenant(Tenant request);

     boolean updateTenant(Tenant request);

     boolean updateTenantStatus(Tenant status);

     boolean deleteTenant(Long tenantId);

    Page<Tenant> getTenants(PageRequest pageRequest);
}
