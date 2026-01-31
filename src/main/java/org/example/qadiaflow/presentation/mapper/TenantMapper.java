package org.example.qadiaflow.presentation.mapper;

import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.presentation.dto.tenant.TenantRequest;
import org.example.qadiaflow.presentation.dto.tenant.TenantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TenantMapper {

//    @Mapping(target = "id", source = "id") // لو TenantRequest فيها id
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Tenant toEntity(TenantRequest req);

    TenantResponse toResponse(Tenant tenant);
}
