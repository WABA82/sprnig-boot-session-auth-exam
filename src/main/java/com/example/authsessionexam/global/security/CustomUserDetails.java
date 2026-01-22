package com.example.authsessionexam.global.security;

import com.example.authsessionexam.domain.auth.entity.AppUser;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails, CredentialsContainer {

    private static final Log log = LogFactory.getLog(CustomUserDetails.class);

    private final Long userId;

    private final String email;

    private String password;

    private final Set<GrantedAuthority> authorities;

    private final boolean enabled;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    // Builder 패턴 사용 권장
    @Builder
    private CustomUserDetails(
            Long userId,
            String email,
            String password,
            Set<GrantedAuthority> authorities,
            boolean enabled,
            boolean accountNonExpired,
            boolean accountNonLocked,
            boolean credentialsNonExpired
    ) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;

    }

    // Entity로부터 생성하는 정적 팩토리 메서드
    public static CustomUserDetails from(AppUser user) {
        return CustomUserDetails.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}
