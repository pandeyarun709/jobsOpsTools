package com.quikr.jobsopstools.service.dto;

import com.quikr.jobsopstools.domain.enumeration.Permission;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.quikr.jobsopstools.domain.JobsUser} entity.
 */
public class JobsUserDTO implements Serializable {
    private Long id;

    private Long babelUserId;

    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    private Long phone;

    private Permission permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBabelUserId() {
        return babelUserId;
    }

    public void setBabelUserId(Long babelUserId) {
        this.babelUserId = babelUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobsUserDTO)) {
            return false;
        }

        return id != null && id.equals(((JobsUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobsUserDTO{" +
            "id=" + getId() +
            ", babelUserId=" + getBabelUserId() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
