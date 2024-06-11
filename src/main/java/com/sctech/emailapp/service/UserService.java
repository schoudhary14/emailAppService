package com.sctech.emailapp.service;

import com.sctech.emailapp.dto.RegisterUserDto;
import com.sctech.emailapp.exceptions.AlreadyExistsException;
import com.sctech.emailapp.exceptions.NotExistsException;
import com.sctech.emailapp.model.User;
import com.sctech.emailapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        if (userRepository.findByEmail(input.getEmail().toLowerCase()).isPresent()) {
            throw new AlreadyExistsException();
        }

        User user = new User();
        user.setCompanyId(input.getCompanyId());
        user.setUserId(input.getUserId());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail().toLowerCase());
        user.setRole(input.getRole());
        user.setStatus(input.getStatus());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        return userRepository.save(user);
    }

    public User getDetail(String userId){
        Optional<User> users = userRepository.findById(userId);

        if (users.isEmpty()) {
            throw new NotExistsException();
        }
        return users.get();
    }

    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User update(String userId, RegisterUserDto input) {
        Optional<User> users = userRepository.findById(userId);

        if (users.isEmpty()) {
            throw new NotExistsException();
        }

        User user = users.get();
        boolean userUpdated = false;

        if(!input.getCompanyId().equals(user.getCompanyId())){
            user.setCompanyId(input.getCompanyId());
            userUpdated = true;
            System.out.println("Entity updated for userId : " + user.getId());
        }

        if (!input.getFirstName().equals(user.getFirstName())){
            user.setFirstName(input.getFirstName());
            userUpdated = true;
            System.out.println("First Name updated for userId : " + user.getId());
        }

        if (!input.getLastName().equals(user.getLastName())){
            user.setLastName(input.getLastName());
            userUpdated = true;
            System.out.println("Last Name updated for userId : " + user.getId());
        }

        if (!input.getEmail().toLowerCase().equals(user.getEmail())){
            user.setEmail(input.getEmail());
            userUpdated = true;
            System.out.println("Email updated for userId : " + user.getId());
        }

        if (!input.getRole().equals(user.getRole())){
            user.setRole(input.getRole());
            userUpdated = true;
            System.out.println("Role updated for userId : " + user.getId());
        }

        if (!input.getStatus().equals(user.getStatus())){
            user.setStatus(input.getStatus());
            userUpdated = true;
            System.out.println("Status updated for userId : " + user.getId());
        }

        if (!passwordEncoder.matches(input.getPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            userUpdated = true;
            System.out.println("Password updated for userId : " + user.getId());
        }

        if(userUpdated){
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            return user;
        }
        return user;
    }
}