package com.rookies4.myspringboot.service;

import com.rookies4.myspringboot.controller.dto.UserDTO;
import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
    @Service @Transactioal(readOnly=true)   // 전체가 읽기전용이 됨
    class UserService {
        @Transactional                      // 등록이나 수정할 때, transaction을 걸어줌
        public UserEntity createUser() { }
    }
    or
    @Service @Transactioal                  // 전체 클래스가 transaction을 걸어줌
    class UserService {
        public UserEntity createUser() { }

        @Transactioal(readOnly=true)        // 등록이나 수정이 많을 경우, 해당 메소드에 한해서 읽기전용이 됨
        public UserEntity getUser() { }
    }

 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    // Repository Injection
    private final UserRepository userRepository;

    // User 등록
    @Transactional
    // Static Inner class라서 UserDTO.UserResponse로 가져올 수 있음
    public UserDTO.UserResponse createUser(UserDTO.UserCreateRequest request) {
        // Email 중복되면 BusinessException을 발생시키고 종료
        userRepository.findByEmail(request.getEmail()) // return-type : Opitonal<UserEntity>
                // Optional의 메소드 (해당 value가 존재하면)
                .ifPresent(entity -> {
                    throw new BusinessException("User with this Email already Exist", HttpStatus.CONFLICT);
                });    // (error 메세지 출력)
        // DTO => Entity로 변환
        UserEntity entity = request.toEntity();
        UserEntity savedEntity = userRepository.save(entity);
        // Entity => DTO로 변환 후 리턴됨
        return new UserDTO.UserResponse(savedEntity);
    }

    // id로 User 조회
    public UserDTO.UserResponse getUserById(Long id) {
        UserEntity userEntity = getUserExist(id);
        return new UserDTO.UserResponse(userEntity);
    }

    // User 목록 조회화기
    public List<UserDTO.UserResponse> getAllUsers() {
        // List<UserEntity> => List<UserDTO.UserRepsonse>
        // 초기버전(복잡한 방법)
//        userRepository.findAll()    // List<UserEntity>
//                .stream()   // Stream<UserEntity>
//                .map(entity -> new UserDTO.UserResponse(entity))   // Stream<UserDTO.UserResponse>
//                .collect(Collectors.toList());   // Stream<UserDTO.UserResponse> => List<UserDTO.UserResponse>

        // 최근버전
        return userRepository.findAll()
                .stream()
                .map(UserDTO.UserResponse::new)
                .toList();
    }

    //User 수정
    @Transactional
    public UserDTO.UserResponse updateUser(String email,
                                           UserDTO.UserUpdateRequest request){
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        //dirty read ( setter 메서드만 호출하고, save() 메서드는 호출하지 않아도 됨)
        existUser.setName(request.getName());
        return new UserDTO.UserResponse(existUser);
    }

    // User 삭제
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = getUserExist(id);
        userRepository.delete(userEntity);
    }

    //내부 Helper Method
    private UserEntity getUserExist(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }


}
