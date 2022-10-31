package com.example.restexample.endpoint;

import com.example.restexample.dto.CreateUserDto;
import com.example.restexample.dto.UserAuthDto;
import com.example.restexample.dto.UserAuthResponseDto;
import com.example.restexample.mapper.UserMapper;
import com.example.restexample.model.Role;
import com.example.restexample.model.User;
import com.example.restexample.repository.UserRepository;
import com.example.restexample.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserEndPoint {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;


    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto){
        Optional<User> userByEmail = userRepository.findByEmail(createUserDto.getEmail());
        if(userByEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserDto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userMapper.map(userRepository.save(user)));
    }

    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto){
        Optional<User> byEmail = userRepository.findByEmail(userAuthDto.getEmail());
        if(byEmail.isPresent()){
            User user = byEmail.get();
            if(passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())){
                return ResponseEntity.ok(UserAuthResponseDto.builder()
                                .token(jwtTokenUtil.generateToken(user.getEmail()))
                                .userDto(userMapper.map(user))
                                .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
