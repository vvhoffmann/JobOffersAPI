package com.hoffmann.joboffersapi.domain.loginandregister;

import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegisterUserRequestDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import static com.hoffmann.joboffersapi.domain.loginandregister.ResponseMessage.USER_NOT_FOUND;

@AllArgsConstructor
@Service
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND.message));
    }

    public RegistrationResultDto register(RegisterUserRequestDto registerUserDto) {
        final User user = User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
        final User savedUser = userRepository.save(user);
        return RegistrationResultDto.builder()
                .username(savedUser.username())
                .id(savedUser.id())
                .created(true)
                .build();

    }
}