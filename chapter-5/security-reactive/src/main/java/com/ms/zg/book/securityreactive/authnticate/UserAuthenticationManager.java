package com.ms.zg.book.securityreactive.authnticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserAuthenticationManager implements ReactiveAuthenticationManager {
    private final ReactiveSessionRepository<? extends Session> sessionRepository;

    @Autowired
    public UserAuthenticationManager(ReactiveSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String sessionId = (String) authentication.getPrincipal();
        String credentials = (String) authentication.getCredentials();
        return sessionRepository.findById(sessionId)
                .map(session -> {
                    Authentication authenticationToken = session.getAttribute("authentication");
                    if (null == authenticationToken || !credentials.equals(authentication.getCredentials())) {
                        throw new CredentialsExpiredException("Credentials is invalidated");
                    }
                    return authenticationToken;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    authentication.setAuthenticated(false);
                    return Mono.just(authentication);
                }));
    }
}


