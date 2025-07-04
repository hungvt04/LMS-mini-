package com.hungvt.userservice.infrastructure.listener;

import com.hungvt.userservice.entity.base.PrimaryEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;
import java.util.UUID;

public class PrimaryEntityListener {

    @PrePersist
    public void onCreate(PrimaryEntity entity) {
        if (entity.getId() == null) {
            entity.setId(this.generateId());
        }
        entity.setCreatedAt(this.getCurrentTime());
        entity.setUpdatedAt(this.getCurrentTime());
        entity.setIsDeleted(false);
    }

    @PreUpdate
    public void onUpdate(PrimaryEntity entity) {
        entity.setUpdatedAt(this.getCurrentTime());
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public Long getCurrentTime() {
        return new Date().getTime();
    }

}
