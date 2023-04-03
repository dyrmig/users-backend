package com.dyrmig.fullstackbackend.controller;

import com.dyrmig.fullstackbackend.exception.UserNotFoundException;
import com.dyrmig.fullstackbackend.model.User;
import com.dyrmig.fullstackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }
    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }
    @PutMapping("/user/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        return userRepository.findById(id)
                .map(user -> {
                   user.setUserName(updatedUser.getUserName());
                   user.setName(updatedUser.getName());
                   user.setEmail(updatedUser.getEmail());
                   return userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User deleted with id: "+id;
    }

}
