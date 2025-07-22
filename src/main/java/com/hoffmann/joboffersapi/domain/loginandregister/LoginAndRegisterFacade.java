package com.hoffmann.joboffersapi.domain.loginandregister;

import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegisterUserRequestDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        return userRepository.findByLogin(username)
                .map(user -> new UserDto(user.id(), user.login(), user.password()))
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.USER_NOT_FOUND.message));
    }

    public RegistrationResultDto register(RegisterUserRequestDto registerUserDto) {
        final User user = User.builder()
                .login(registerUserDto.login())
                .password(registerUserDto.password())
                .build();
        final User savedUser = userRepository.save(user);
        return RegistrationResultDto.builder()
                .login(savedUser.login())
                .id(savedUser.id())
                .created(true)
                .build();

    }
}