package br.com.drianodev.finance_api.service;

import br.com.drianodev.finance_api.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();
    User authenticateUser(String email, String password);
    User saveUser(User user);
    void validateEmail(String email);
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    void deleteUser(Long id);
}
