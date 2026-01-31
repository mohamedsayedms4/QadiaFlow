package org.example.qadiaflow.presentation.dto.tenant;

import org.example.qadiaflow.domain.model.TenantStatus;

public record TenantUpdateRequest(
        Long id,
        String name ,
        TenantStatus status
) {
}
