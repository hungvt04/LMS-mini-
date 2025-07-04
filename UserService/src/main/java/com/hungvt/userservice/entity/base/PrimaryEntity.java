package com.hungvt.userservice.entity.base;

import com.hungvt.userservice.infrastructure.listener.PrimaryEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(PrimaryEntityListener.class)
public class PrimaryEntity {

    @Id
    private String id;

    private Long createdAt;

    private Long updatedAt;

    private Boolean isDeleted;

}
