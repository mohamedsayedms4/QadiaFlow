package org.example.qadiaflow.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.exception.BadRequestException;
import org.example.qadiaflow.application.exception.NotFoundException;
import org.example.qadiaflow.application.port.out.TenantPort;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.infrastructure.persistence.jpa.JpaTenantRepository;
import org.example.qadiaflow.presentation.i18n.MessageUtil.MessageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TenantAdapter implements TenantPort {

    private final MessageUtil msg;
    private final JpaTenantRepository repo;

    @Override
    public Tenant getRequired(Long tenantId) {
        return repo.findById(tenantId)
                .orElseThrow(() -> new NotFoundException(msg.get("tenant.not_found")));
    }

    @Override
    public boolean addTenant(Tenant tenant) {
        repo.save(tenant);
        return true;
    }

    @Override
    public boolean updateTenant(Tenant tenant) {
        if (tenant.getId() == null) {
            throw new BadRequestException("Tenant id is required for update");
        }
        if (!repo.existsById(tenant.getId())) {
            throw new NotFoundException(msg.get("tenant.not_found"));
        }

        repo.save(tenant);
        return true;
    }

    @Override
    @Transactional
    public boolean updateTenantStatus(Tenant tenant) {
        if (tenant.getId() == null) {
            throw new BadRequestException("Tenant id is required to update status");
        }

        Tenant existing = getRequired(tenant.getId());
        existing.setStatus(tenant.getStatus());

        repo.save(existing);
        return true;
    }

    @Override
    public boolean deleteTenant(Long tenantId) {
        if (!repo.existsById(tenantId)) {
            throw new NotFoundException(msg.get("tenant.not_found"));
        }
        repo.deleteById(tenantId);
        return true;
    }

    @Override
    public Page<Tenant> getTenants(PageRequest pageRequest) {
        return repo.findAll(pageRequest);
    }
}
