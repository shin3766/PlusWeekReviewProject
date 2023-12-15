package com.starta.plusweekreviewproject.service;

import com.starta.plusweekreviewproject.dto.CheckUsernameRequestDto;
import com.starta.plusweekreviewproject.dto.LoginRequestDto;
import com.starta.plusweekreviewproject.dto.SignupRequestDto;
import com.starta.plusweekreviewproject.entity.User;
import com.starta.plusweekreviewproject.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String checkPassword = requestDto.getCheckPassword();

        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        if(!password.equals(checkPassword)) {
            throw new IllegalArgumentException("확인 비밀번호가 같지 않습니다.");
        }

        User user = new User(username, password);

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

}
