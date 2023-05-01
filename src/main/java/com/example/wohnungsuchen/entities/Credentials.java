package com.example.wohnungsuchen.entities;

import com.example.wohnungsuchen.auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "сredentials")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Credentials implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_name", nullable = false)
    @NotBlank
    @Length(min = 3)
    private String profile_name;
    @Column(name = "surname", nullable = false)
    @NotBlank
    @Length(min = 3)
    private String surname;
    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "(\\+\\d{3}\\d{9})" +
            "|(\\+\\d{2}\\(\\d{3}\\)\\d{7})|" +
            "(\\(\\d{3}\\)\\d{7})|" +
            "(0\\(\\d{3}\\)\\d{6})")
    @NotBlank
    private String phone;
    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    private String email;
    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private Date birthDate;
    @Column(name = "profile_password", nullable = false)
    @NotBlank
    @Length(min = 8)
    private String password;
    @Column(name = "verified")
    private Boolean verified;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @Column(name = "activation_code")
    private String activationCode;

    public Credentials() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return profile_name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
