package com.hungvt.userservice.entity;

import com.hungvt.userservice.entity.base.PrimaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
/**
 * password khong co thi duoc dang ky tu email
 */
public class User extends PrimaryEntity {

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String fullName;

    private String gender;

    private String avatar;

}
