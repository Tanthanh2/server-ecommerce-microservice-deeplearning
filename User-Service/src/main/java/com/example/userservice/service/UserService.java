package com.example.userservice.service;

import com.example.userservice.User.UserDTO;
import com.example.userservice.Entity.User;

public interface UserService {
    boolean checkUserExist(String email, String phone);
    User updateUser(UserDTO userDTO);
    com.example.userservice.Entity.User getUserById(Long _id);
    boolean updatePassword(Long id,String passOld, String passNew);
}
