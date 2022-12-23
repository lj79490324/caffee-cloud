package com.coffee.auth.security.jwt;

import com.coffee.auth.security.model.SecurityUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * Jwt的token配置
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 18:16
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    private Object credentials;
    private SecurityUserDetails securityUserDetails;

    public JwtAuthenticationToken(SecurityUserDetails securityUserDetails) {
        super(null);
        this.securityUserDetails = securityUserDetails;
        this.principal = securityUserDetails.getUsername();
        this.credentials = securityUserDetails.getPassword();
    }

    @Override
    public Object getCredentials() {
        return securityUserDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return securityUserDetails.getUsername();
    }

    public SecurityUserDetails getSecurityUserDetails() {
        return securityUserDetails;
    }

    public void setSecurityUserDetails(SecurityUserDetails securityUserDetails) {
        this.securityUserDetails = securityUserDetails;
    }
}
