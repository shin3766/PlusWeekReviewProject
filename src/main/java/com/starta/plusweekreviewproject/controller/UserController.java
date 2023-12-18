package com.starta.plusweekreviewproject.controller;

import com.starta.plusweekreviewproject.dto.CheckUsernameRequestDto;
import com.starta.plusweekreviewproject.dto.CommonResponseDto;
import com.starta.plusweekreviewproject.dto.LoginRequestDto;
import com.starta.plusweekreviewproject.dto.SignupRequestDto;
import com.starta.plusweekreviewproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/checkusername")
    public ResponseEntity<CommonResponseDto> checkUsername(@RequestBody CheckUsernameRequestDto requestDto) {

        try {
            userService.checkUsername(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new CommonResponseDto("사용가능한 닉네임입니다.", HttpStatus.CONTINUE.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            userService.signup(signupRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.checkValidLogin(requestDto);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        
        // jwt토큰을 뱉어주는 메서드
        userService.login(requestDto, res);

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }
}
