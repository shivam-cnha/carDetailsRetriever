package com.example.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.entity.LoginRequest;
import com.example.client.entity.User;
import com.example.client.event.RegistrationCompleteEvent;
import com.example.client.model.PasswordModel;
import com.example.client.model.UserModel;
import com.example.client.service.UserService;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        // one user successfully registered RegistrationCompleteEvent is published
        // Takes Application Event for that create a package
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        String status = userService.verifyLogin(loginRequest);
        return status;
    }

    // Verification of token number in db and user had recieved verification link
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User VerifiesSuccessfully";
        } else {
            return " Bad User ";
        }
    }
 
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
