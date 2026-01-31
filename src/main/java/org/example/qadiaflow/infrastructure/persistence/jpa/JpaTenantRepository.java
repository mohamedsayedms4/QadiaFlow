package org.example.qadiaflow.infrastructure.persistence.jpa;

import org.example.qadiaflow.domain.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTenantRepository extends JpaRepository<Tenant, Long> {


}
