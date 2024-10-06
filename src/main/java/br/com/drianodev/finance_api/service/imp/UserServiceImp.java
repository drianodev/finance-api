package br.com.drianodev.finance_api.service.imp;

import br.com.drianodev.finance_api.exceptions.AuthenticationErrorException;
import br.com.drianodev.finance_api.exceptions.BusinessRulesException;
import br.com.drianodev.finance_api.exceptions.UserNotFoundException;
import br.com.drianodev.finance_api.model.entity.User;
import br.com.drianodev.finance_api.model.repository.UserRepository;
import br.com.drianodev.finance_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) throw new AuthenticationErrorException("Usuário não encontrado!");

        if (!user.get().getPassword().equals(password)) throw new AuthenticationErrorException("Senha inválida!");

        return user.get();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void validateEmail(String email) {
        boolean thereIsEmail = userRepository.existsByEmail(email);

        if (thereIsEmail) throw new AuthenticationErrorException("Email já cadastrado!");
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user == null || user.getId() == null) throw new BusinessRulesException("Usuário inválido!");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) throw new UserNotFoundException("Usuário não encontrado com o ID: " + id);
    }
}
