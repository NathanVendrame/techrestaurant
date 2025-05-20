package br.com.fiap.techrestaurant.controllers;

import br.com.fiap.techrestaurant.dtos.LoginDto;
import br.com.fiap.techrestaurant.entities.User;
import br.com.fiap.techrestaurant.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        List<User> users = this.userService.findAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> findUser(
            @PathVariable("userId") Long userId) {
        Optional<User> user = this.userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(
            @RequestBody User user) {
        this.userService.saveUser(user);
        HttpStatus status = HttpStatus.CREATED;
        return ResponseEntity.status(status.value()).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user) {
        this.userService.updateUser(user, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateUser(
            @RequestBody LoginDto loginDto) {
        Boolean valid = this.userService.validateUser(loginDto);
        return ResponseEntity.ok(valid);
    }
}
