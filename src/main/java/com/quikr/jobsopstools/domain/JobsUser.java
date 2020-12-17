package com.quikr.jobsopstools.domain;

import com.quikr.jobsopstools.domain.enumeration.Permission;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobsUser.
 */
@Entity
@Table(name = "jobs_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "babel_user_id")
    private Long babelUserId;

    @Pattern(regexp = "^(.+)@(.+)$")
    @Column(name = "email")
    private String email;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "phone")
    private Long phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Permission permission;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBabelUserId() {
        return babelUserId;
    }

    public JobsUser babelUserId(Long babelUserId) {
        this.babelUserId = babelUserId;
        return this;
    }

    public void setBabelUserId(Long babelUserId) {
        this.babelUserId = babelUserId;
    }

    public String getEmail() {
        return email;
    }

    public JobsUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public JobsUser phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Permission getPermission() {
        return permission;
    }

    public JobsUser permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobsUser)) {
            return false;
        }
        return id != null && id.equals(((JobsUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobsUser{" +
            "id=" + getId() +
            ", babelUserId=" + getBabelUserId() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
