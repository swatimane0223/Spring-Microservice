package org.management.authservice.service;

import org.management.authservice.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
   public Optional<User> findByEmail(String email);
}
