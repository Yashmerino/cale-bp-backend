package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User related endpoints.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<SuccessDTO> createUser(@RequestBody UserDTO userDTO) {
        this.userService.createUser(userDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "User created successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable(value = "id") Long id) {
        return this.userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDTO userDTO) {
        return this.userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDTO> deleteUser(@PathVariable(value = "id") Long id) {
        this.userService.deleteUser(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "User deleted successfully"), HttpStatus.OK);
    }
}
