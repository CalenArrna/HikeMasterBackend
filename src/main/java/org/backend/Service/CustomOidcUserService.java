package org.backend.Service;

import org.backend.Model.Authority;
import org.backend.Model.GoogleUserInfo;
import org.backend.Model.HikeMasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @PersistenceContext
    EntityManager em;


    UserService userService;

    @Autowired
    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    public OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());
        if (em.createQuery("SELECT u from HikeMasterUser u WHERE u.email=:mail", HikeMasterUser.class).setParameter("mail", googleUserInfo.getEmail()).getResultList() != null) {
            HikeMasterUser user = new HikeMasterUser();
            Authority userAuthority = userService.getUserAuthority();
            user.setEmail(googleUserInfo.getEmail());
            user.setUsername(googleUserInfo.getName());
            user.getAuthoritySet().add(userAuthority);
            userAuthority.getSecurityHikeMasterUsers().add(user);
            user.setRole(em.createQuery("select a FROM Authority a WHERE a.roleName='USER'", Authority.class).getSingleResult().getRoleName());
            em.persist(user);
        }
        return oidcUser;
    }
}
