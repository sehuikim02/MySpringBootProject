package com.rookies4.myspringboot.controller;

import com.rookies4.myspringboot.controller.dto.UserDTO;
import com.rookies4.myspringboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserServiceRestController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO.UserResponse> create(
            // @Valid : 검증, @RequestBody : json 데이터를 자바 객체로 변환
            @Valid @RequestBody UserDTO.UserCreateRequest request) {
        UserDTO.UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.UserResponse> getUserById (@PathVariable Long id) {
        UserDTO.UserResponse userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO.UserResponse>> getAllUserss() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO.UserResponse> updateUser(@PathVariable String email,
                                                           @Valid @RequestBody UserDTO.UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(email,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}

