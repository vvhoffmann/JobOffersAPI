package com.hoffmann.joboffersapi.domain.loginandregister;


import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegisterUserRequestDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new InMemoryUserRepository()
    );

    @Test
    @DisplayName("Should throw UserNotFoundException when user not found")
    public void should_throw_exception_when_user_not_found() {
        //given
        String login = "test";
        //when
        Throwable thrown = catchThrowable(()-> loginAndRegisterFacade.findByUsername(login));
        //then
        assertThat(thrown).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Should find user by login")
    public void should_find_user_by_login() {
        //given
        RegisterUserRequestDto registerUserRequestDto = RegisterUserRequestDto.builder()
                .login("login")
                .password("password")
                .build();
        loginAndRegisterFacade.register(registerUserRequestDto);
        //when
        final UserDto userDto = loginAndRegisterFacade.findByUsername("login");
        //then
        assertThat(userDto.login()).isEqualTo(registerUserRequestDto.login());
        assertThat(userDto.password()).isEqualTo(registerUserRequestDto.password());
    }

    @Test
    @DisplayName("Should register user")
    public void should_register_user() {
        //given
        RegisterUserRequestDto registerUserRequestDto = RegisterUserRequestDto.builder()
                .login("login")
                .password("password")
                .build();
        //when
        final RegistrationResultDto registeredUser = loginAndRegisterFacade.register(registerUserRequestDto);
        //then
        assertTrue(registeredUser.created());
        assertThat(registeredUser.login()).isEqualTo("login");
    }





}