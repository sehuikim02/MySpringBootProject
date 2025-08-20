package com.rookies4.myspringboot.controller;

import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor    // lombok에서 final로 선언된 변수들을 초기화 하는 생성자를 자동으로 만들어줌
@RequestMapping("/api/users1")      // url path가 중복되면 안됨
public class UserRestController {
    // @AutoWired   // Setter Injection
    private final UserRepository userRepository;

    // Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        System.out.println("생성자 Injection " + userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }

    // 1. 등록
    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    // 2. 조회
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    // 2-1. ID로 조회
    @GetMapping("/{id}")    // /api/users/1, /api/users/2
    public UserEntity getUser(@PathVariable Long id) {
        UserEntity existUser = getExistUser(id);
        return existUser;
    }

    // 2-2. Email로 조회하고, 수정
    @PatchMapping("/{email}/")
    public UserEntity updateUser(@PathVariable String email, @RequestBody UserEntity userDetail){
        UserEntity existUser = userRepository.findByEmail(email) //Optional<UserEntity>
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        //name 변경
        existUser.setName(userDetail.getName());
        //DB에 저장
        UserEntity updateUser = userRepository.save(existUser);
        return updateUser;
    }

    // 2-3. 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        UserEntity existUser = getExistUser(id);

        // DB에서 삭제 요청
        userRepository.delete(existUser);
        return ResponseEntity.ok("User가 삭제 되었습니다.");
    }

    private UserEntity getExistUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        // orElseThrow(Supplier) Supplier의 추상메소드 T get()
        UserEntity existUser = optionalUser
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }
}
