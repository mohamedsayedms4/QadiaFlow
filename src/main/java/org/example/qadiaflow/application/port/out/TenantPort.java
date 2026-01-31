package org.example.qadiaflow.application.port.out;

import org.example.qadiaflow.domain.model.Tenant;

public interface TenantPort {
    Tenant getRequired(Long tenantId);
}
