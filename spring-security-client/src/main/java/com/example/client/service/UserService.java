package com.example.client.service;


import com.example.client.entity.LoginRequest;
import com.example.client.entity.User;
import com.example.client.model.UserModel;

public interface UserService {

    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    User findByEmail(String email);



    String verifyLogin(LoginRequest loginRequest);

}