package com.koyeb.AnotherWebProject.service;

import com.koyeb.AnotherWebProject.db.UserEntity;
import com.koyeb.AnotherWebProject.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> readAll(){
        return userRepository.findAll();
    }
}
