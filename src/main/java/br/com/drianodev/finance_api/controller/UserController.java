package br.com.drianodev.finance_api.controller;

import br.com.drianodev.finance_api.exceptions.AuthenticationErrorException;
import br.com.drianodev.finance_api.exceptions.BusinessRulesException;
import br.com.drianodev.finance_api.model.dto.UserDTO;
import br.com.drianodev.finance_api.model.entity.User;
import br.com.drianodev.finance_api.service.LaunchService;
import br.com.drianodev.finance_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LaunchService launchService;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO) {
        try {
            User authenticateUser = userService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(authenticateUser);
        } catch (AuthenticationErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword()).build();

        try {
            User savedUser = userService.saveUser(user);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        } catch (AuthenticationErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity getBalance(@PathVariable("id") String id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        BigDecimal balance = launchService.getBalanceByUser(id);
        return ResponseEntity.ok(balance);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        User updatedUser = user.get();
        updatedUser.setName(userDTO.getName());
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setPassword(userDTO.getPassword());
        try {
            userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (BusinessRulesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
