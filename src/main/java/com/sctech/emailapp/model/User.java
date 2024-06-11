package com.sctech.emailapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sctech.emailapp.enums.EntityStatus;
import com.sctech.emailapp.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "usersDetails")
public class User implements UserDetails {
    @Id
    private String id;
    private String companyId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private EntityStatus status;

    @JsonIgnore
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean isAccountNonExpired;

    @JsonIgnore
    private boolean isAccountNonLocked;

    @JsonIgnore
    private boolean isCredentialsNonExpired;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

}