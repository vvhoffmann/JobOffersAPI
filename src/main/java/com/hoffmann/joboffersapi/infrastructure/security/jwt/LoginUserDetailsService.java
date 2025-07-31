package com.hoffmann.joboffersapi.infrastructure.security.jwt;

import com.hoffmann.joboffersapi.domain.loginandregister.LoginAndRegisterFacade;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(final String username) throws BadCredentialsException {
        final UserDto byUsername = loginAndRegisterFacade.findByUsername(username);
        return getUser(byUsername);
    }

    private org.springframework.security.core.userdetails.User getUser(UserDto user) {
        return new User(
                user.username(),
                user.password(),
                Collections.emptyList());
    }
}