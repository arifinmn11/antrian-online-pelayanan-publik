package com.arifinmn.projectapi.controllers;

import com.arifinmn.projectapi.entities.Users;
import com.arifinmn.projectapi.exceptions.ApplicationExceptions;
import com.arifinmn.projectapi.models.requests.UserRequest;
import com.arifinmn.projectapi.models.responses.ResponseMessage;
import com.arifinmn.projectapi.repositories.UserRepository;
import com.arifinmn.projectapi.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseMessage<?> create(@RequestBody UserRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String fullName = request.getFullName();
        System.out.println(fullName);
        if (!request.getCode().equalsIgnoreCase("ADMIN")) {
            throw new ApplicationExceptions(HttpStatus.BAD_REQUEST, "Something wrong input!");
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        Users entity = userRepository.save(new Users(username, encodedPassword, fullName));
        return ResponseMessage.success(entity);
    }
}