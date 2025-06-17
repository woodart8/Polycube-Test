package kr.co.polycube.backendtest.user.service;

import kr.co.polycube.backendtest.common.exception.ErrorCode;
import kr.co.polycube.backendtest.common.exception.CommonException;
import kr.co.polycube.backendtest.user.dto.UserDto;
import kr.co.polycube.backendtest.user.dto.UserIdDto;
import kr.co.polycube.backendtest.user.dto.UserNameDto;
import kr.co.polycube.backendtest.user.entity.User;
import kr.co.polycube.backendtest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// 회원 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // user 등록
    @Transactional
    public UserIdDto createUser(UserNameDto userNameDto) {
        if (!StringUtils.hasText(userNameDto.getName())) {
            throw new CommonException(ErrorCode.INVALID_USER_NAME);
        }

        User newUser = userRepository.save(
                User.builder()
                    .name(userNameDto.getName())
                    .build()
        );

        return UserIdDto.builder()
                        .id(newUser.getId())
                        .build();
    }

    // user 조회
    public UserDto getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserDto.builder()
                      .id(user.getId())
                      .name(user.getName())
                      .build();
    }

    // user 수정
    @Transactional
    public UserDto updateUser(long id, UserNameDto userNameDto) {
        if (!StringUtils.hasText(userNameDto.getName())) {
            throw new CommonException(ErrorCode.INVALID_USER_NAME);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserDto.builder()
                      .id(user.getId())
                      .name(user.getName())
                      .build();
    }

}
