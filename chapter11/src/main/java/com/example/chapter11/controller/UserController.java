package com.example.chapter11.controller;

import com.example.chapter11.domain.User;
import com.example.chapter11.dto.ApiResponse;
import com.example.chapter11.dto.UserCreateRequest;
import com.example.chapter11.dto.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User API", description = "회원 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<Long, User> userStore = new LinkedHashMap<>();
    private Long sequence = 1L;

    @Operation(summary = "회원 목록 조회", description = "저장된 모든 회원 정보를 조회한다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        List<User> users = new ArrayList<>(userStore.values());
        return ResponseEntity.ok(new ApiResponse<>(true, users, "회원 목록 조회 성공"));
    }

    @Operation(summary = "회원 단건 조회", description = "회원 ID로 특정 회원 정보를 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(
            @Parameter(description = "조회할 회원 ID", example = "1")
            @PathVariable Long id) {

        User user = userStore.get(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "해당 회원이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, user, "회원 조회 성공"));
    }

    @Operation(summary = "회원 등록", description = "새로운 회원 정보를 등록한다.")
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserCreateRequest request) {
        User user = new User(sequence++, request.getName(), request.getEmail());
        userStore.put(user.getId(), user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, user, "회원 등록 성공"));
    }

    @Operation(summary = "회원 수정", description = "회원 ID에 해당하는 회원 정보를 수정한다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @Parameter(description = "수정할 회원 ID", example = "1")
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request) {

        User user = userStore.get(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "해당 회원이 존재하지 않습니다."));
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return ResponseEntity.ok(new ApiResponse<>(true, user, "회원 수정 성공"));
    }

    @Operation(summary = "회원 삭제", description = "회원 ID에 해당하는 회원 정보를 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "삭제할 회원 ID", example = "1")
            @PathVariable Long id) {

        User removedUser = userStore.remove(id);

        if (removedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "해당 회원이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, null, "회원 삭제 성공"));
    }
}