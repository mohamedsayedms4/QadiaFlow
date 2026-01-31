package org.example.qadiaflow.presentation.dto.tenant;

import org.example.qadiaflow.domain.model.TenantStatus;

public record TenantRequest(
        String name
//        TenantStatus status
) {
}
