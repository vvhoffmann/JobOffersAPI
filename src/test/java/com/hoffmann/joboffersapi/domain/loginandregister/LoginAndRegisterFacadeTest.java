package com.hoffmann.joboffersapi.domain.loginandregister;


import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegisterUserRequestDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
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
        String username = "test";
        //when
        Throwable thrown = catchThrowable(()-> loginAndRegisterFacade.findByUsername(username));
        //then
        assertThat(thrown).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("Should find user by username")
    public void should_find_user_by_login() {
        //given
        RegisterUserRequestDto registerUserRequestDto = RegisterUserRequestDto.builder()
                .username("username")
                .password("password")
                .build();
        loginAndRegisterFacade.register(registerUserRequestDto);
        //when
        final UserDto userDto = loginAndRegisterFacade.findByUsername("username");
        //then
        assertThat(userDto.username()).isEqualTo(registerUserRequestDto.username());
        assertThat(userDto.password()).isEqualTo(registerUserRequestDto.password());
    }

    @Test
    @DisplayName("Should register user")
    public void should_register_user() {
        //given
        RegisterUserRequestDto registerUserRequestDto = RegisterUserRequestDto.builder()
                .username("username")
                .password("password")
                .build();
        //when
        final RegistrationResultDto registeredUser = loginAndRegisterFacade.register(registerUserRequestDto);
        //then
        assertTrue(registeredUser.created());
        assertThat(registeredUser.username()).isEqualTo("username");
    }
}