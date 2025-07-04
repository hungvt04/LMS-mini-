package com.hungvt.userservice.entity;

import com.hungvt.userservice.entity.base.PrimaryEntity;
import com.hungvt.userservice.infrastructure.constant.EntityConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role extends PrimaryEntity {

    @Column(length = EntityConstant.NAME_LENGTH, unique = true)
    private String roleName;

    private String description;

}
