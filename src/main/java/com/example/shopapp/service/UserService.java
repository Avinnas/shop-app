package com.example.shopapp.service;

import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public User findUserByEmail(String email) ;
    public User saveUser(User user);
}
