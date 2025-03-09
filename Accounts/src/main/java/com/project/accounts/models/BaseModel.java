package com.project.accounts.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@ToString
public class BaseModel {

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;
    @NotNull
    @Max(value = 20)
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;
    @Max(value = 20)
    @Column(name = "UPDATED_BY", insertable = false)
    private String updatedBy;

}
