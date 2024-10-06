package br.com.drianodev.finance_api.service;

import br.com.drianodev.finance_api.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User authenticateUser(String email, String password);
    User saveUser(User user);
    void validateEmail(String email);
    Optional<User> getUserById(String id);
    User updateUser(User user);
    void deleteUser(String id);
}
