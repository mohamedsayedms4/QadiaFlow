package org.example.qadiaflow.model;


import jakarta.persistence.Entity;

@Entity
public class Tenant extends BaseEntity{
    private String name;


    private TenantStatus status ;
}
