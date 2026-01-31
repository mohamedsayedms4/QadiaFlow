package org.example.qadiaflow.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.exception.NotFoundException;
import org.example.qadiaflow.application.port.out.TenantPort;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.infrastructure.persistence.jpa.JpaTenantRepository;
import org.example.qadiaflow.presentation.i18n.MessageUtil.MessageUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantAdapter implements TenantPort {

    private final MessageUtil msg;
    private final JpaTenantRepository repo;

    @Override
    public Tenant getRequired(Long tenantId) {
        return repo.findById(tenantId).orElseThrow(() -> new NotFoundException(msg.get("tenant.not_found")));
    }
}
