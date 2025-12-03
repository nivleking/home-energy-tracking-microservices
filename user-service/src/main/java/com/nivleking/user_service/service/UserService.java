package com.nivleking.user_service.service;

import com.nivleking.user_service.dto.UserDto;
import com.nivleking.user_service.entity.User;
import com.nivleking.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto createUser(UserDto input) {
        log.info("Creating user: {}", input);
        User createdUser = User.builder()
                .name(input.getName())
                .surname(input.getSurname())
                .email(input.getEmail())
                .address(input.getAddress())
                .alerting(input.isAlerting())
                .energyAlertingThreshold(input.getEnergyAlertingThreshold())
                .build();

        User saved = userRepository.save(createdUser);
        return toDto(saved);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }

    public UserDto getUserById(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public void updateUser(Long id, UserDto userDto) {
        log.info("Updating user with id: {} and input: {}", id, userDto);
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setAlerting(userDto.isAlerting());
        user.setEnergyAlertingThreshold(userDto.getEnergyAlertingThreshold());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        userRepository.delete(user);
    }
}
