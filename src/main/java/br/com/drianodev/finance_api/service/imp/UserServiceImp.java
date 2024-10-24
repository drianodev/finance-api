package br.com.drianodev.finance_api.service.imp;

import br.com.drianodev.finance_api.exceptions.AuthenticationErrorException;
import br.com.drianodev.finance_api.exceptions.BusinessRulesException;
import br.com.drianodev.finance_api.exceptions.UserNotFoundException;
import br.com.drianodev.finance_api.model.entity.User;
import br.com.drianodev.finance_api.model.repository.UserRepository;
import br.com.drianodev.finance_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(value = "users", key = "#email")
    public User authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        log.info("User - {}", user);
        log.info("User - {}", user.get().getEmail());
        log.info("User - {}", user.get().getPassword());
        if (user.isEmpty()) throw new AuthenticationErrorException("Usuário não encontrado!");

        if (!user.get().getPassword().equals(password)) throw new AuthenticationErrorException("Senha inválida!");

        return user.get();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        log.info("User - {}", user);
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void validateEmail(String email) {
        boolean thereIsEmail = userRepository.existsByEmail(email);

        if (thereIsEmail) throw new AuthenticationErrorException("Email já cadastrado!");
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user == null || user.getId() == null) throw new BusinessRulesException("Usuário inválido!");
        log.info("user - {}", user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) throw new UserNotFoundException("Usuário não encontrado com o ID: " + id);
        userRepository.deleteById(id);
    }
}
