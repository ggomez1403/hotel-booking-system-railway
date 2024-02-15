package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.UserDto;
import com.ggomezr.bookingsystem.domain.entity.User;
import com.ggomezr.bookingsystem.domain.exceptions.UserNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public void updateUser(Integer id, UserDto userDto) throws UserNotFoundException {
        User existingUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());

        if(userDto.password() != null){
            existingUser.setPassword(passwordEncoder.encode(userDto.password()));
        }

        existingUser.setPhoneNumber(userDto.phoneNumber());

        if(userDto.role() != null){
            existingUser.setRole(userDto.role());
        }

        userRepository.save(existingUser);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
