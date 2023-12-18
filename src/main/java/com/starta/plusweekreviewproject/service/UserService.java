package com.starta.plusweekreviewproject.service;

import com.starta.plusweekreviewproject.dto.CheckUsernameRequestDto;
import com.starta.plusweekreviewproject.dto.LoginRequestDto;
import com.starta.plusweekreviewproject.dto.SignupRequestDto;
import com.starta.plusweekreviewproject.entity.User;
import com.starta.plusweekreviewproject.entity.UserRoleEnum;
import com.starta.plusweekreviewproject.jwt.JwtUtil;
import com.starta.plusweekreviewproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String checkPassword = passwordEncoder.encode(requestDto.getCheckPassword());

        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        if(!password.equals(checkPassword)) {
            throw new IllegalArgumentException("확인 비밀번호가 같지 않습니다.");
        }

        // 사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀려 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    public void checkUsername(CheckUsernameRequestDto requestDto) {
        String username = requestDto.getUsername();

        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요");
        }
    }

    public void checkValidLogin(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요."));

        if(!password.equals(user.getPassword())){
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
    }

}
